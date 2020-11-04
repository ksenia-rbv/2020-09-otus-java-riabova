package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    public void run(String className){
        try {
            Class<?> testClass = Class.forName(className);

            Method[] methodsAll = testClass.getDeclaredMethods();
            Method beforeMethod = Arrays.stream(methodsAll)
                    .filter(method -> method.isAnnotationPresent(Before.class))
                    .findFirst()
                    .orElse(null);
            Method afterMethod = Arrays.stream(methodsAll)
                    .filter(method -> method.isAnnotationPresent(After.class))
                    .findFirst()
                    .orElse(null);
            List<Method> testMethods = Arrays.stream(methodsAll)
                    .filter(method -> method.isAnnotationPresent(Test.class))
                    .collect(Collectors.toList());

            run(testClass, testMethods, beforeMethod, afterMethod);
        }
        catch (Exception e){
            System.out.println("Error during test process: "+ e);
        }
    }

    private void run(Class<?> testClass, List<Method> testMethods, Method beforeMethod, Method afterMethod) throws Exception {
        int errorCounter = 0;
        for (Method testMethod : testMethods) {
            Object testClassInstance = testClass.getConstructor().newInstance();
            boolean result = run(testClassInstance, testMethod, beforeMethod, afterMethod);
            if (!result) errorCounter++;
        }
        System.out.println("--- Statistics: ---");
        System.out.println(testMethods.size() + " test completed, " + errorCounter + " failed");
    }

    private boolean run(Object testClassInstance, Method testMethod, Method beforeMethod, Method afterMethod) throws Exception {
        boolean result;
        if (beforeMethod != null) {
            beforeMethod.invoke(testClassInstance);
        }
        try {
            testMethod.invoke(testClassInstance);
            System.out.println("Test " + testMethod.getName() + " passed successful.");
            result = true;
        } catch (Exception e) {
            result = false;
            System.out.println("Test " + testMethod.getName() + " failed.");
        }
        if (afterMethod != null) {
            afterMethod.invoke(testClassInstance);
        }
        return result;
    }
}
