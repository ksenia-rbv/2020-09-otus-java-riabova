package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String tableName = entityClassMetaData.getName();
        return String.format("select * from %s", tableName);
    }

    @Override
    public String getSelectByIdSql() {
        String tableName = entityClassMetaData.getName();
        String idName = entityClassMetaData.getIdField().getName();
        return String.format("select * from %s where %s = ?", tableName, idName);
    }

    @Override
    public String getInsertSql() {
        String tableName = entityClassMetaData.getName();

        List<Field> allFields = entityClassMetaData.getAllFields();
        String columns = allFields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        String questionMarks = allFields.stream()
                .map(f -> "?")
                .collect(Collectors.joining(", "));

        return String.format("insert into %s(%s) values (%s)", tableName, columns, questionMarks);
    }

    @Override
    public String getUpdateSql() {
        String tableName = entityClassMetaData.getName();
        String columns = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .map(n -> n + " = ?")
                .collect(Collectors.joining(", "));
        String idName = entityClassMetaData.getIdField().getName();

        return String.format("update %s set %s where %s = ?", tableName, columns, idName);
    }
}
