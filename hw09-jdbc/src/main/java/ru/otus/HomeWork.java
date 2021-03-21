package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.ClientDao;
import ru.otus.core.model.Account;
import ru.otus.core.model.Client;
import ru.otus.core.service.DbServiceClientImpl;
import ru.otus.demo.DataSourceDemo;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.ClientDaoImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.util.Optional;


public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DataSourceDemo();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);

// Работа с пользователем
        DbExecutorImpl<Client> clientDbExecutor = new DbExecutorImpl<>();
        JdbcMapper<Client> jdbcMapperClient = new JdbcMapperImpl<>(sessionManager, clientDbExecutor);

// Работа через маппер
        jdbcMapperClient.insert(new Client(8, "Nick", 22));
        System.out.println("Client after insert(): " + jdbcMapperClient.findById(8, Client.class));

        jdbcMapperClient.update(new Client(8, "Kolya", 22));
        System.out.println("Client after update(): " + jdbcMapperClient.findById(8, Client.class));

        jdbcMapperClient.insertOrUpdate(new Client(8, "Kolya", 23));
        System.out.println("Client after insertOrUpdate(), updated client: " + jdbcMapperClient.findById(8, Client.class));

        jdbcMapperClient.insertOrUpdate(new Client(9, "Petya", 12));
        System.out.println("Client after insertOrUpdate(), inserted client: " + jdbcMapperClient.findById(9, Client.class));

// Код дальше должен остаться, т.е. clientDao должен использоваться
        ClientDao clientDao = new ClientDaoImpl(sessionManager, clientDbExecutor);
        var dbServiceClient = new DbServiceClientImpl(clientDao);
        var id = dbServiceClient.saveClient(new Client(0, "dbServiceClient", 13));
        Optional<Client> clientOptional = dbServiceClient.getClient(id);

        clientOptional.ifPresentOrElse(
                client -> logger.info("created client, name:{}", client.getName()),
                () -> logger.info("client was not created")
        );

// Работа со счетом
        DbExecutorImpl<Account> accountDbExecutor = new DbExecutorImpl<>();
        JdbcMapper<Account> accountJdbcMapper = new JdbcMapperImpl<>(sessionManager, accountDbExecutor);

        accountJdbcMapper.insert(new Account("1", "rub", 123.5));
        System.out.println("Account after insert(): " + accountJdbcMapper.findById("1", Account.class));

        accountJdbcMapper.update(new Account("1", "rub", 500.5));
        System.out.println("Account after update(): " + accountJdbcMapper.findById("1", Account.class));

        accountJdbcMapper.insertOrUpdate(new Account("1", "rub", 200.5));
        System.out.println("Account after insertOrUpdate(), updated account: " + accountJdbcMapper.findById("1", Account.class));

        accountJdbcMapper.insertOrUpdate(new Account("2", "amd", 1000.5));
        System.out.println("Account after insertOrUpdate(), inserted account: " + accountJdbcMapper.findById("2", Account.class));
    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.clean();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }
}
