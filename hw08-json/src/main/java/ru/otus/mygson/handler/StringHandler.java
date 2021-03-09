package ru.otus.mygson.handler;

public class StringHandler implements TypeHandler {
    @Override
    public boolean isSuitable(Object obj) {
        return obj instanceof String;
    }

    @Override
    public void append(StringBuilder stringBuilder, Object obj) {
        HandlerUtils.appendWithQuotes(stringBuilder, obj);
    }
}
