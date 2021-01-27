package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Clock;
import java.time.LocalDateTime;

public class ProcessorThrowException implements Processor {
    private Clock clock = Clock.systemUTC();

    void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Message process(Message message) {
        var sec = LocalDateTime.now(clock).getSecond();
        if (sec % 2 == 0) {
            throw new RuntimeException("Even second!");
        }
        return message;
    }
}
