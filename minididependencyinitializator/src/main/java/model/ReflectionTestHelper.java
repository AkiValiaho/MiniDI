package model;

import java.lang.reflect.Field;

/**
 * Created by Aki on 28.9.2017.
 */
class ReflectionTestHelper<T> {
    Object getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Class<?> aClass = object.getClass();
        Field field = getField(aClass, fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private Field getField(Class<?> aClass, String fieldName) throws NoSuchFieldException {
        return aClass.getDeclaredField(fieldName);
    }
}
