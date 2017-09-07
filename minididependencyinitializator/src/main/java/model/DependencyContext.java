package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds a map that contains all the resolved dependencies
 * Created by akivv on 5.9.2017.
 */
public class DependencyContext {

    private final Map<Class<?>, Object> dependencyHashMap;

    public DependencyContext() {
        this.dependencyHashMap = new HashMap<>();
    }

    public void addDependencyToMap(Dependency dependency) {
        dependency.addDependencyToMap(this);
    }

    void registerDependencyAttributes(Class<?> classToRegister, Object instance) {
        dependencyHashMap.put(classToRegister, instance);
    }
}