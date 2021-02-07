package ru.otus.processor.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.Clock;
import java.time.Instant;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessorThrowExceptionTest {

    @Test
    void testProcessThrownAssertionInEvenSecond() {
        var message = new Message.Builder(1L)
                .field1("field1")
                .build();

        Clock clockEvenSecond = Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), UTC);
        ProcessorThrowException processorThrowException = new ProcessorThrowException(clockEvenSecond);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            processorThrowException.process(message);
        });
        assertThat(exception.getMessage()).isEqualTo("Even second!");
    }

    @Test
    void testProcessDontThrownExceptionInOddSecond() {
        var message = new Message.Builder(1L)
                .field1("field1")
                .build();

        Clock clockOddSecond = Clock.fixed(Instant.parse("2007-12-03T10:15:31.00Z"), UTC);
        ProcessorThrowException processorThrowException = new ProcessorThrowException(clockOddSecond);
        assertDoesNotThrow(() -> processorThrowException.process(message));
    }
}