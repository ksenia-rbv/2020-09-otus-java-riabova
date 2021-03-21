package ru.otus.jdbc.dao;

import ru.otus.core.dao.ClientDao;
import ru.otus.core.model.Client;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private final JdbcMapperImpl<Client> jdbcMapper;

    public ClientDaoImpl(SessionManagerJdbc sessionManager, DbExecutorImpl<Client> dbExecutor) {
        this.jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor);
    }

    @Override
    public Optional<Client> findById(long id) {
        Client client = jdbcMapper.findById(id, Client.class);
        return Optional.ofNullable(client);
    }

    @Override
    public long insert(Client client) {
        jdbcMapper.insert(client);
        return client.getId();
    }

    @Override
    public void update(Client client) {
        jdbcMapper.update(client);
    }

    @Override
    public long insertOrUpdate(Client client) {
        jdbcMapper.insertOrUpdate(client);
        return client.getId();
    }

    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }
}
