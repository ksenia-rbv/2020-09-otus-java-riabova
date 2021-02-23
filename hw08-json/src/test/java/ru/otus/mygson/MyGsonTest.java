package ru.otus.mygson;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class MyGsonTest {
    private final MyGson myGson = new MyGson();
    private final Gson gson = new Gson();

    @Test
    void testNullObject() {
        TestObject testObject = null;
        String jsonObject = myGson.toJson(testObject);

        TestObject testObjectDeser = gson.fromJson(jsonObject, TestObject.class);
        Assertions.assertNull(testObjectDeser);
    }

    @Test
    void testMyGson() {
        int[] arr = {1, 2, 3, 4};
        TestObject testObject = new TestObject(22,
                "test",
                10,
                67.8,
                arr,
                List.of(4, 5, 2, 3),
                Set.of("a", "d", "s"));
        String jsonObject = myGson.toJson(testObject);

        TestObject testObjectDeser = gson.fromJson(jsonObject, TestObject.class);
        Assertions.assertEquals(testObject,testObjectDeser);
    }

    @Test
    void testUnknownType() {
        int[] arr = {1, 2, 3, 4};
        TestObjectWithUnknownType testObject = new TestObjectWithUnknownType(new TestObject(22,
                "test",
                10,
                67.8,
                arr,
                List.of(4, 5, 2, 3),
                Set.of("a", "d", "s")));

        Assertions.assertThrows(NotFoundTypeHandlerException.class, () -> myGson.toJson(testObject));
    }

    @Test
    void testMyGsonWithNullField() {
        int[] arr = {1, 2, 3, 4};
        TestObject testObject = new TestObject(22,
                null,
                10,
                67.8,
                arr,
                List.of(4, 5, 2, 3),
                Set.of("a", "d", "s"));
        String jsonObject = myGson.toJson(testObject);

        TestObject testObjectDeser = gson.fromJson(jsonObject, TestObject.class);
        Assertions.assertEquals(testObject,testObjectDeser);
    }
}