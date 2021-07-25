package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

import static ru.otus.crm.service.HibernateServiceClientInitializer.getDbServiceClient;
import static ru.otus.crm.service.HibernateServiceClientInitializer.init;

/*
    // Стартовая страница
    http://localhost:8080
*/
public class ClientsWebServerDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        DBServiceClient dbServiceClient = getDbServiceClient();
        init(dbServiceClient);

        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        ClientsWebServer clientsWebServer = new ClientsWebServerImpl(WEB_SERVER_PORT,
                authService, userDao, dbServiceClient, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
