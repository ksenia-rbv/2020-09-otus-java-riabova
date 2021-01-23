package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoggingInvocationHandler implements InvocationHandler {

    private final MyClassInterface myClass;
    private final Set<String> methodsWithLogging;

    public LoggingInvocationHandler(MyClassInterface myClass) {
        this.myClass = myClass;
        this.methodsWithLogging = analyzeMethodsForLogging(myClass);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (methodsWithLogging.contains(getMethodIdentifier(method))) {
            System.out.println("executed method: " + method.getName() + ", param: " + Arrays.asList(args));
        }

        return method.invoke(myClass, args);
    }

    private String getMethodIdentifier(Method method) {
        String originalMethod = method.toString();
        return originalMethod.substring(originalMethod.indexOf(method.getName()));
    }

    private Set<String> analyzeMethodsForLogging(MyClassInterface myClass) {
        return Stream.of(myClass.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .map(this::getMethodIdentifier)
                .collect(Collectors.toSet());
    }
}
