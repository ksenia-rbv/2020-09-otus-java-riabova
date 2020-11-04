package ru.otus;

public class TestRunnerApplication {
    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        testRunner.run(args[0]);
    }
}
