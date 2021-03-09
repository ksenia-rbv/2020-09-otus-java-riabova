package ru.otus.mygson.handler;

public interface TypeHandler {

    boolean isSuitable(Object obj);

    void append(StringBuilder stringBuilder, Object obj);
}
