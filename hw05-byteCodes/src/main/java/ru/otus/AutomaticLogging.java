package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

class AutomaticLogging {

    private AutomaticLogging() {
    }

    static MyClassInterface createMyClass() {
        InvocationHandler handler = new LoggingInvocationHandler(new MyClassImpl());
        return (MyClassInterface) Proxy.newProxyInstance(AutomaticLogging.class.getClassLoader(),
                new Class<?>[]{MyClassInterface.class}, handler);
    }

}
