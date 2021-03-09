package ru.otus.mygson.handler;

import java.util.Collection;

public class CollectionHandler extends ArrayHandler {
    @Override
    public boolean isSuitable(Object obj) {
        return obj instanceof Collection;
    }

    @Override
    public void append(StringBuilder stringBuilder, Object obj) {
        super.append(stringBuilder, ((Collection<?>) obj).toArray());
    }
}
