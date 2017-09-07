package tooling;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by akivv on 5.9.2017.
 */
public class ReflectionInitializerTool {
    public Object initialize(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (isLeafParameter(dependencyClass)) {
            return instantiateWithNoArgsConstructor(dependencyClass);
        }
        //Dive into the argument list to create everything
        //TODO Do this
        return null;
    }

    private Object instantiateWithNoArgsConstructor(Class<?> dependencyClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Optional<Constructor<?>> noArgsConstructor = findNoArgsConstructor(dependencyClass);
        if (noArgsConstructor.isPresent()) {
            return noArgsConstructor.get().newInstance(null);
        }
        return null;
    }

    private boolean isLeafParameter(Class<?> dependencyClass) {
        Optional<Constructor<?>> noArgsConstructor = findNoArgsConstructor(dependencyClass);
        if (!noArgsConstructor.isPresent()) {
            return false;
        }
        return true;
    }

    private Optional<Constructor<?>> findNoArgsConstructor(Class<?> dependencyClass) {
        return Arrays.stream(dependencyClass.getConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findFirst();
    }
}
