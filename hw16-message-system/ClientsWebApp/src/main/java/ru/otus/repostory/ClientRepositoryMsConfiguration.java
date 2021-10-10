package ru.otus.repostory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.mappers.ClientDtoMapper;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.repostory.handlers.GetAllClientsRequestHandler;
import ru.otus.repostory.handlers.SaveClientRequestHandler;

import java.util.Set;

@Configuration
public class ClientRepositoryMsConfiguration {

    public static final String CLIENT_REPOSITORY_MS_CLIENT_NAME = "ClientRepository";

    @Bean
    @Qualifier(value = CLIENT_REPOSITORY_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createGetAllClientsRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        return new GetAllClientsRequestHandler(clientRepository, clientDtoMapper);
    }

    @Bean
    @Qualifier(value = CLIENT_REPOSITORY_MS_CLIENT_NAME)
    RequestHandler<? extends ResultDataType> createSaveClientRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        return new SaveClientRequestHandler(clientRepository, clientDtoMapper);
    }

    @Bean(value = CLIENT_REPOSITORY_MS_CLIENT_NAME)
    MsClient createClientServiceMsClient(MessageSystem messageSystem,
                                         @Qualifier(value = CLIENT_REPOSITORY_MS_CLIENT_NAME) Set<RequestHandler<? extends ResultDataType>> handlers,
                                         CallbackRegistry callbackRegistry) {
        MsClientImpl clientService = new MsClientImpl(CLIENT_REPOSITORY_MS_CLIENT_NAME, messageSystem, new HandlersStoreImpl(handlers), callbackRegistry);
        messageSystem.addClient(clientService);
        return clientService;
    }


}
