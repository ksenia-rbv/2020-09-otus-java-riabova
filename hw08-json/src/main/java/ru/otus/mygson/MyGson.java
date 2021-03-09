package ru.otus.mygson;

import ru.otus.mygson.handler.*;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Objects.isNull;
import static ru.otus.mygson.handler.HandlerUtils.appendWithQuotes;

public class MyGson {
    private static final List<TypeHandler> HANDLERS = List.of(
            new ArrayHandler(),
            new CollectionHandler(),
            new PrimitiveAndNumberHandler(),
            new StringHandler());

    public String toJson(Object obj) {
        if (isNull(obj)) {
            return "null";
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        StringBuilder json = new StringBuilder("{");
        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                if (isNull(value)) {
                    continue;
                }
                appendWithQuotes(json, field.getName()).append(":");
                getTypeHandler(value).append(json, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            json.append(",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }

    private TypeHandler getTypeHandler(Object object) {
        for (TypeHandler handler : HANDLERS) {
            if (handler.isSuitable(object)) {
                return handler;
            }
        }
        throw new NotFoundTypeHandlerException(object.getClass());
    }
}
