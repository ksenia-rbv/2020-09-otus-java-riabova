package ru.otus.model;

import java.util.List;

public class ObjectForMessage implements Cloneable{
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage clone() {
        var clone = new ObjectForMessage();
        clone.setData(List.copyOf(data));
        return clone;
    }

    @Override
    public String toString() {
        return (data == null)? "null": data.toString();
    }
}
