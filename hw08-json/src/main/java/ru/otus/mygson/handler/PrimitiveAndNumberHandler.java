package ru.otus.mygson.handler;

public class PrimitiveAndNumberHandler implements TypeHandler {
    @Override
    public boolean isSuitable(Object obj) {
        return obj.getClass().isPrimitive() || obj instanceof Number;
    }

    @Override
    public void append(StringBuilder stringBuilder, Object obj) {
        stringBuilder.append(obj);
    }

}
