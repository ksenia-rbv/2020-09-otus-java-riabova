package ru.otus.services.handlers;

import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.message.MessageType;

import static ru.otus.messagesystem.message.MessageType.CLIENTS_LIST;

public class ClientsListResponseHandlerImpl extends BaseResponseHandler {
    public ClientsListResponseHandlerImpl(CallbackRegistry callbackRegistry) {
        super(callbackRegistry);
    }

    @Override
    public MessageType getMessageType() {
        return CLIENTS_LIST;
    }
}
