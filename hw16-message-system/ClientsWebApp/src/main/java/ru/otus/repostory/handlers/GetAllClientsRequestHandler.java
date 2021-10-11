package ru.otus.repostory.handlers;

import ru.otus.dto.ClientDto;
import ru.otus.dto.ClientsListDto;
import ru.otus.mappers.ClientDtoMapper;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.repostory.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.messagesystem.message.MessageType.CLIENTS_LIST;

public class GetAllClientsRequestHandler implements RequestHandler<ClientsListDto> {

    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;

    public GetAllClientsRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoMapper = clientDtoMapper;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        List<ClientDto> allClientsList = clientRepository.findAll().stream()
                .map(clientDtoMapper::createClientDto)
                .collect(Collectors.toList());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, getMessageType(), new ClientsListDto(allClientsList)));
    }

    @Override
    public MessageType getMessageType() {
        return CLIENTS_LIST;
    }


}
