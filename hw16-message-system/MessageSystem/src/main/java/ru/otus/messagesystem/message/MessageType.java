package ru.otus.messagesystem.message;

public enum MessageType {
    CLIENT("Client"),
    CLIENTS_LIST("ClientsList");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
