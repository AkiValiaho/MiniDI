package model;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds a map that contains all the resolved dependencies
 * Created by akivv on 5.9.2017.
 */
public class DependencyContext {

    private Map<Class<?>, Object> dependencyHashMap;

    public DependencyContext() {
        this.dependencyHashMap = new HashMap<>();
    }

    public void addDependencyToMap(Dependency dependency) {
        dependency.addDependencyToMap(this);
    }

    void registerDependencyAttributes(Class<?> classToRegister, Object instance) {
        dependencyHashMap.put(classToRegister, instance);
    }

    public List<Object> getParamInstancesForConstructor(Constructor declaringClass) {
 return        Arrays.stream(declaringClass.getParameterTypes())
                .map(paramType -> dependencyHashMap.get(paramType))
                .collect(Collectors.toList());
    }
}
