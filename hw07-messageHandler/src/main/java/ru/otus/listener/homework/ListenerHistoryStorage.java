package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.LinkedList;
import java.util.List;

public class ListenerHistoryStorage implements Listener {

    private final List<Record> historyStorage = new LinkedList<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        var record = new Record(oldMsg.copy(), newMsg.copy());
        historyStorage.add(record);
    }

    public List<Record> getHistory() {
        return historyStorage;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        for (Record e : historyStorage) {
            stringBuilder.append(e.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    static class Record {
        private final Message oldMessage;
        private final Message newMessage;

        public Record(Message oldMessage, Message newMessage) {
            this.oldMessage = oldMessage;
            this.newMessage = newMessage;
        }

        public Message getOldMessage() {
            return oldMessage;
        }

        public Message getNewMessage() {
            return newMessage;
        }

        @Override
        public String toString() {
            return oldMessage + " - " + newMessage;
        }
    }
}
