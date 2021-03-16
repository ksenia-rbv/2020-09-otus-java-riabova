package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void insert(T objectData) {
        var entityClassMetaData = new EntityClassMetaDataImpl<>((Class<T>) objectData.getClass());
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        var fields = entityClassMetaData.getAllFields();

        insertOrUpdate(objectData, entitySQLMetaData.getInsertSql(), fields);
    }

    @Override
    public void update(T objectData) {
        var entityClassMetaData = new EntityClassMetaDataImpl<>((Class<T>) objectData.getClass());
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        var fields = entityClassMetaData.getFieldsWithoutId();
        fields.add(entityClassMetaData.getIdField());

        insertOrUpdate(objectData, entitySQLMetaData.getUpdateSql(), fields);
    }

    private void insertOrUpdate(T objectData, String sqlQuery, List<Field> fields) {
        List<Object> params = fields.stream()
                .map(f -> getValueFromObject(objectData, f))
                .collect(Collectors.toList());
        try {
            sessionManager.beginSession();
            dbExecutor.executeInsert(getConnection(), sqlQuery, params);
            sessionManager.commitSession();
        } catch (SQLException e) {
            sessionManager.rollbackSession();
            throw new RuntimeException(e);
        }
    }

    private Object getValueFromObject(T objectData, Field field) {
        try {
            field.setAccessible(true);
            return field.get(objectData);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        var entityClassMetaData = new EntityClassMetaDataImpl<>((Class<T>) objectData.getClass());
        Field idField = entityClassMetaData.getIdField();
        Object id = getValueFromObject(objectData, idField);
        T objectFromDb = findById(id, (Class<T>) objectData.getClass());
        if (objectFromDb == null) {
            insert(objectData);
        } else {
            update(objectData);
        }
    }

    @Override
    public T findById(Object id, Class<T> clazz) {
        var entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
        var entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
        var selectByIdSql = entitySQLMetaData.getSelectByIdSql();
        var rsHandler = getRsHandlerForFindById(entityClassMetaData);

        try {
            sessionManager.beginSession();

            return dbExecutor.executeSelect(getConnection(), selectByIdSql, id, rsHandler)
                    .orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    private Function<ResultSet, T> getRsHandlerForFindById(EntityClassMetaData<T> entityClassMetaData) {
        return rs -> {
            try {
                if (rs.next()) {
                    Object[] params = entityClassMetaData.getAllFields().stream()
                            .map(Field::getName)
                            .map(n -> {
                                try {
                                    return rs.getObject(n);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .toArray();
                    return entityClassMetaData.getConstructor().newInstance(params);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        };
    }
}
