package ru.otus.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.services.handlers.ClientResponseHandlerImpl;
import ru.otus.services.handlers.ClientsListResponseHandlerImpl;

import java.util.Set;

@Configuration
public class ClientServiceMsConfiguration {

    public static final String CLIENT_SERVICE_MS_CLIENT_NAME = "ClientService";

    @Bean
    @Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createClientResponseHandler(CallbackRegistry callbackRegistry) {
        return new ClientResponseHandlerImpl(callbackRegistry);
    }

    @Bean
    @Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createClientsListResponseHandler(CallbackRegistry callbackRegistry) {
        return new ClientsListResponseHandlerImpl(callbackRegistry);
    }

    @Bean(value = CLIENT_SERVICE_MS_CLIENT_NAME)
    MsClient createWebMsClient(MessageSystem messageSystem,
                               @Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME) Set<RequestHandler<? extends ResultDataType>> handlers,
                               CallbackRegistry callbackRegistry) {
        MsClientImpl clientService = new MsClientImpl(CLIENT_SERVICE_MS_CLIENT_NAME, messageSystem, new HandlersStoreImpl(handlers), callbackRegistry);
        messageSystem.addClient(clientService);
        return clientService;
    }

}
