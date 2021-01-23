package ru.otus;

public class AutomaticLoggingDemo {
    public static void main(String[] args) {
        MyClassInterface myClass = AutomaticLogging.createMyClass();
        myClass.calculation(1);
        myClass.calculation(2, 3);
        myClass.calculation(4, 5, "hello");
    }
}