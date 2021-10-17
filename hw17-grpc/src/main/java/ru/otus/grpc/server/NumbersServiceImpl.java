package ru.otus.grpc.server;

import io.grpc.stub.StreamObserver;
import ru.otus.grpc.util.Utils;
import ru.otus.protobuf.generated.NumbersRequestMessage;
import ru.otus.protobuf.generated.NumbersResponseMessage;
import ru.otus.protobuf.generated.RemoteNumbersServiceGrpc;

public class NumbersServiceImpl extends RemoteNumbersServiceGrpc.RemoteNumbersServiceImplBase {

    public NumbersServiceImpl() {
    }

    @Override
    public void getNumbers(NumbersRequestMessage request, StreamObserver<NumbersResponseMessage> responseObserver) {
        final NumbersGenerator numbersGenerator = new NumbersGeneratorImpl(request.getFirstValue(), request.getSecondValue());
        int value;
        while ((value = numbersGenerator.getNext()) >= 0) {
            Utils.sleepSec(2);
            responseObserver.onNext(
                    NumbersResponseMessage.newBuilder()
                            .setValue(value)
                            .build());
        }
        responseObserver.onCompleted();
    }


}
