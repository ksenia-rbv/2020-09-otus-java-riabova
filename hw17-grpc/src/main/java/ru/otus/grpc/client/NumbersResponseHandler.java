package ru.otus.grpc.client;

import ru.otus.protobuf.generated.NumbersResponseMessage;

public interface NumbersResponseHandler {

    void onResponse(NumbersResponseMessage responseMessage);
}
