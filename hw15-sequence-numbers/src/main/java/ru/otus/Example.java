package ru.otus;

import java.util.concurrent.CyclicBarrier;

public class Example {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        ExampleThread exampleThread2 = new ExampleThread(2, cyclicBarrier);
        ExampleThread exampleThread1 = new ExampleThread(1, cyclicBarrier);
        exampleThread2.start();
        exampleThread1.start();
    }
}
