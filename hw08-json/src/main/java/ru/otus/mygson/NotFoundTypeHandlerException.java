package ru.otus.mygson;

public class NotFoundTypeHandlerException extends RuntimeException{
    public NotFoundTypeHandlerException(Class<?> clazz) {
        super("Not found handler for " + clazz);
    }
}
