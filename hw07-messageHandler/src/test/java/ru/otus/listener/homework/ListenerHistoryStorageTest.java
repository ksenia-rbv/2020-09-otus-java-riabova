package ru.otus.listener.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListenerHistoryStorageTest {

    private ListenerHistoryStorage listenerHistoryStorage;

    @BeforeEach
    void init() {
        listenerHistoryStorage = new ListenerHistoryStorage();
    }

    @Test
    void onUpdatedStoreSameRecords() {
        var message1 = createMessage(1);
        var message2 = createMessage(2);
        var message3 = createMessage(3);
        var message4 = createMessage(4);

        listenerHistoryStorage.onUpdated(message1, message2);
        listenerHistoryStorage.onUpdated(message3, message4);

        var records = listenerHistoryStorage.getHistory();

        assertThat(records).hasSize(2);

        var record0 = records.get(0);
        assertThat(record0.getOldMessage()).isEqualTo(message1);
        assertThat(record0.getNewMessage()).isEqualTo(message2);

        var record1 = records.get(1);
        assertThat(record1.getOldMessage()).isEqualTo(message3);
        assertThat(record1.getNewMessage()).isEqualTo(message4);
    }

    @Test
    void onUpdateStoreRecordWithMessageWithDontChangedObjectFields() {
        var objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.of("1", "2"));

        var message1 = createMessage(1, objectForMessage);
        var message2 = createMessage(2);

        listenerHistoryStorage.onUpdated(message1, message2);

        var records = listenerHistoryStorage.getHistory();
        var record = records.get(0);
        assertThat(getDataFromOldMessageField13(record)).containsExactly("1", "2");

        objectForMessage.setData(List.of("3", "4"));

        records = listenerHistoryStorage.getHistory();
        record = records.get(0);
        assertThat(getDataFromOldMessageField13(record)).containsExactly("1", "2");
    }

    private List<String> getDataFromOldMessageField13(ListenerHistoryStorage.Record record) {
        return record.getOldMessage().getField13().getData();
    }

    private Message createMessage(long id) {
        return new Message.Builder(1L)
                .field1(String.valueOf(id))
                .build();
    }

    private Message createMessage(long id, ObjectForMessage objectForMessage) {
        return new Message.Builder(1L)
                .field1(String.valueOf(id))
                .field13(objectForMessage)
                .build();
    }

}