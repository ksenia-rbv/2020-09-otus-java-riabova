package ru.otus.mygson.handler;

public class HandlerUtils {
    public static StringBuilder appendWithQuotes(StringBuilder stringBuilder, Object value) {
        return stringBuilder.append("\"").append(value).append("\"");
    }
}
