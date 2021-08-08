package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        List<Method> methods = getAllConfigMethods(configClass);
        Object configObject = createConfigObject(configClass);

        methods.forEach(method -> {
            Object component = createComponent(configObject, method);
            appComponents.add(component);
            String componentName = method.getAnnotation(AppComponent.class).name();
            appComponentsByName.put(componentName, component);
        });
    }

    private Object createConfigObject(Class<?> configClass) {
        try {
            return configClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create config object", e);
        }
    }

    private Object createComponent(Object configObject, Method method) {
        Object[] args = getParameters(method);
        try {
            return method.invoke(configObject, args);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create component", e);
        }
    }

    private Object[] getParameters(Method method) {
        Parameter[] parameters = method.getParameters();

        if (parameters.length == 0) {
            return new Object[0];
        }
        return Arrays.stream(parameters)
                .map(parameter -> getAppComponent(parameter.getType()))
                .toArray();
    }

    private List<Method> getAllConfigMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
