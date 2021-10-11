package ru.otus.repostory.handlers;

import ru.otus.dto.ClientDto;
import ru.otus.mappers.ClientDtoMapper;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.model.Client;
import ru.otus.repostory.ClientRepository;

import java.util.Optional;

import static ru.otus.messagesystem.message.MessageType.CLIENT;

public class SaveClientRequestHandler implements RequestHandler<ClientDto> {

    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;

    public SaveClientRequestHandler(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoMapper = clientDtoMapper;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        ClientDto clientDto = MessageHelper.getPayload(msg);
        Client savedClient = clientRepository.save(clientDtoMapper.createClient(clientDto));
        clientDto.setId(savedClient.getId());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, getMessageType(), clientDto));
    }

    @Override
    public MessageType getMessageType() {
        return CLIENT;
    }
}
