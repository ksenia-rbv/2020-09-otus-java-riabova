package ru.otus;


public class MyClassImpl implements MyClassInterface {

    @Log
    @Override
    public void calculation(int param1) {
        System.out.println("calculation(int)");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("calculation(int,int)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("calculation(int,int,String)");
    }
}
