package ru.otus.services.handlers;

import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.message.MessageType;

import static ru.otus.messagesystem.message.MessageType.CLIENT;

public class ClientResponseHandlerImpl extends BaseResponseHandler {
    public ClientResponseHandlerImpl(CallbackRegistry callbackRegistry) {
        super(callbackRegistry);
    }

    @Override
    public MessageType getMessageType() {
        return CLIENT;
    }
}
