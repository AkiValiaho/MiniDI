package model;

import java.lang.reflect.Constructor;

public class ReflectionUtils implements ReflectionToolSet {
    @Override
    public Constructor<?>[] getDeclaredConstructors(Class<?> dependencyClass) {
        return dependencyClass.getDeclaredConstructors();
    }
}
