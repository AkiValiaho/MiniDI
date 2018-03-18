package model;

import java.lang.reflect.Constructor;

public class ReflectionUtils {
    public Constructor<?>[] getDeclaredConstructors(Class<?> dependencyClass) {
        return dependencyClass.getDeclaredConstructors();
    }
}
