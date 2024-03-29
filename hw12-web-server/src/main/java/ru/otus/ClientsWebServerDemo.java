package ru.otus;

import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.HibernateServiceClientConfiguration;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

import java.util.List;

/*
    // Стартовая страница
    http://localhost:8080
*/
public class ClientsWebServerDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        DBServiceClient dbServiceClient = new HibernateServiceClientConfiguration().getDbServiceClient();
        dbDemoInit(dbServiceClient);

        UserDao userDao = new InMemoryUserDao();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        ClientsWebServer clientsWebServer = new ClientsWebServerImpl(WEB_SERVER_PORT,
                authService, dbServiceClient, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }

    private static void dbDemoInit(DBServiceClient dbServiceClient) {
        dbServiceClient.saveClient(new Client("client1",
                new AddressDataSet("One"), List.of(new PhoneDataSet("111"))));
        dbServiceClient.saveClient(new Client("client2",
                new AddressDataSet("Two"), List.of(new PhoneDataSet("222"))));
    }
}
