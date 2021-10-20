package ru.otus.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.util.Utils;
import ru.otus.protobuf.generated.NumbersResponseMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientCounter implements NumbersResponseHandler, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ClientCounter.class);

    private final int firstValue;
    private final int endValue;
    private final CountDownLatch latchForComplete;

    private final AtomicInteger lastServiceValue = new AtomicInteger(0);

    public ClientCounter(int firstValue, int endValue, CountDownLatch latchForComplete) {
        this.firstValue = firstValue;
        this.endValue = endValue;
        this.latchForComplete = latchForComplete;
    }

    @Override
    public void onResponse(NumbersResponseMessage responseMessage) {
        synchronized (this) {
            lastServiceValue.set(responseMessage.getValue());
        }
    }

    @Override
    public void run() {
        logger.info("ClientCounter is starting...");
        for (int i = firstValue; i < endValue + 1; ) {
            Utils.sleepSec(1);
            synchronized (this) {
                i = i + lastServiceValue.getAndSet(0) + 1;
                logger.info("currentValue: {}", i);
            }
        }
        logger.info("ClientCounter completed");
        latchForComplete.countDown();
    }

}
