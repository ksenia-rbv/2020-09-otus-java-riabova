package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClassExample {

    @Before
    public void beforeTest() {
        System.out.println("Before");
    }

    @Test
    public void test1() {
        System.out.println("Test1");
    }

    @Test
    public void test2() {
        System.out.println("Test2");
    }

    @Test
    public void test3() {
        System.out.println("Test3");
        int i = 12 / 0;
    }

    @After
    public void afterTest() {
        System.out.println("After");
    }

}
