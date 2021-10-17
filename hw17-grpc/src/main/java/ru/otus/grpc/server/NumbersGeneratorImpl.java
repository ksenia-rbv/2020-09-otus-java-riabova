package ru.otus.grpc.server;

public class NumbersGeneratorImpl implements NumbersGenerator {
    private final int end;
    private int count;

    public NumbersGeneratorImpl(int start, int end) {
        this.count = start;
        this.end = end;
    }

    @Override
    public int getNext() {
        if (count >= end) {
            return -1;
        }
        return ++count;
    }
}
