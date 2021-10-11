package ru.otus.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.dto.ClientDto;
import ru.otus.dto.ClientsListDto;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;

import static ru.otus.messagesystem.message.MessageType.CLIENT;
import static ru.otus.messagesystem.message.MessageType.CLIENTS_LIST;
import static ru.otus.repostory.ClientRepositoryMsConfiguration.CLIENT_REPOSITORY_MS_CLIENT_NAME;
import static ru.otus.services.ClientServiceMsConfiguration.CLIENT_SERVICE_MS_CLIENT_NAME;

@Service
public class ClientServiceImpl implements ClientService {

    private final MsClient msClient;

    public ClientServiceImpl(@Qualifier(value = CLIENT_SERVICE_MS_CLIENT_NAME) MsClient msClient) {
        this.msClient = msClient;
    }

    @Override
    public void save(ClientDto clientDto, MessageCallback<ClientDto> callback) {
        Message outMsg = msClient.produceMessage(CLIENT_REPOSITORY_MS_CLIENT_NAME, clientDto, CLIENT, callback);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void findAll(MessageCallback<ClientsListDto> callback) {
        Message outMsg = msClient.produceMessage(CLIENT_REPOSITORY_MS_CLIENT_NAME, new ClientsListDto(), CLIENTS_LIST, callback);
        msClient.sendMessage(outMsg);
    }

}
