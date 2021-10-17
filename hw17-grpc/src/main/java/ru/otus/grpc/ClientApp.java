package ru.otus.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.otus.grpc.client.ClientCounter;
import ru.otus.grpc.client.ClientStreamObserver;
import ru.otus.protobuf.generated.NumbersRequestMessage;
import ru.otus.protobuf.generated.RemoteNumbersServiceGrpc;

import java.util.concurrent.CountDownLatch;

import static ru.otus.grpc.ConnectionUtils.SERVER_HOST;
import static ru.otus.grpc.ConnectionUtils.SERVER_PORT;

public class ClientApp {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        RemoteNumbersServiceGrpc.RemoteNumbersServiceStub stub = RemoteNumbersServiceGrpc.newStub(channel);

        CountDownLatch latchForComplete = new CountDownLatch(2);
        ClientCounter clientCounter = new ClientCounter(0, 50, latchForComplete);
        ClientStreamObserver clientStreamObserver = new ClientStreamObserver(clientCounter, latchForComplete);

        new Thread(clientCounter).start();
        stub.getNumbers(NumbersRequestMessage.newBuilder()
                        .setFirstValue(0)
                        .setSecondValue(30)
                        .build(),
                clientStreamObserver);

        latchForComplete.await();

        channel.shutdown();
    }
}
