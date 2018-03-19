package model;

import java.lang.reflect.Constructor;

public interface ReflectionToolSet {
    Constructor<?>[] getDeclaredConstructors(Class<?> dependencyClass);
}
