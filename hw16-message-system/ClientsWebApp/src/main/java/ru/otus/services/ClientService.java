package ru.otus.services;

import ru.otus.dto.ClientDto;
import ru.otus.dto.ClientsListDto;
import ru.otus.messagesystem.client.MessageCallback;

public interface ClientService {
    void findAll(MessageCallback<ClientsListDto> callback);

    void save(ClientDto clientDto, MessageCallback<ClientDto> callback);
}
