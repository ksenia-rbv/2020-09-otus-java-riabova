package ru.otus.grpc.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.NumbersResponseMessage;

import java.util.concurrent.CountDownLatch;

public class ClientStreamObserver implements StreamObserver<NumbersResponseMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);

    private final NumbersResponseHandler numbersResponseHandler;
    private final CountDownLatch latchForComplete;

    public ClientStreamObserver(NumbersResponseHandler numbersResponseHandler, CountDownLatch latchForComplete) {
        this.numbersResponseHandler = numbersResponseHandler;
        this.latchForComplete = latchForComplete;
    }

    @Override
    public void onNext(NumbersResponseMessage value) {
        logger.info("new value: {}", value.getValue());
        numbersResponseHandler.onResponse(value);
    }

    @Override
    public void onError(Throwable t) {
        logger.error("Observer error", t);
    }

    @Override
    public void onCompleted() {
        logger.info("Observe completed");
        latchForComplete.countDown();
    }
}
