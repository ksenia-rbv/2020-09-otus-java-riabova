package ru.otus.jdbc.mapper;

import ru.otus.jdbc.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final List<Field> allFields;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        allFields = List.of(clazz.getDeclaredFields());
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        Class<?>[] constructorParameterTypes = allFields.stream()
                .map(Field::getType)
                .toArray(size -> new Class<?>[size]);
        try {
            return clazz.getConstructor(constructorParameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return allFields.stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find field with annotation @Id"));
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return allFields.stream()
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }
}
