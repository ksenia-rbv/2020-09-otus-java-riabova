package ru.otus.mygson.handler;

import java.lang.reflect.Array;

public class ArrayHandler implements TypeHandler {
    @Override
    public boolean isSuitable(Object obj) {
        return obj.getClass().isArray();
    }

    @Override
    public void append(StringBuilder stringBuilder, Object obj) {
        stringBuilder.append("[");
        for (int i = 0; i < Array.getLength(obj); i++) {
            Object element = Array.get(obj, i);
            if (element.getClass().isPrimitive() || element instanceof Number) {
                stringBuilder.append(element);
            } else {
                HandlerUtils.appendWithQuotes(stringBuilder, element);
            }
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
    }
}
