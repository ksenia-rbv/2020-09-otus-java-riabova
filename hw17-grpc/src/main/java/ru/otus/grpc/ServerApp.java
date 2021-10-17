package ru.otus.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.grpc.server.NumbersServiceImpl;

import java.io.IOException;

import static ru.otus.grpc.ConnectionUtils.SERVER_PORT;

public class ServerApp {

    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        NumbersServiceImpl numbersService = new NumbersServiceImpl();

        Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(numbersService)
                .build();
        server.start();
        logger.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
