package ru.otus;

public class Example {

    private final static Object monitor = new Object();

    public static void main(String[] args) {
        ExampleThread exampleThread1 = new ExampleThread("Thread1", monitor);
        ExampleThread exampleThread2 = new ExampleThread("Thread2", monitor);
        exampleThread1.start();
        exampleThread2.start();
    }
}
