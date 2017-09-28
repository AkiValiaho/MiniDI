package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Holds a map that contains all the resolved dependencies
 * Created by akivv on 5.9.2017.
 */
public class DependencyContext {

    private Map<Class<?>, Object> dependencyHashMap;

    public DependencyContext() {
        this.dependencyHashMap = new HashMap<>();
    }

    void addDependencyToMap(Dependency dependency) {
        dependency.addDependencyToMap(this);
    }

    void registerDependencyAttributes(Class<?> classToRegister, Object instance) {
        dependencyHashMap.put(classToRegister, instance);
    }

    public Optional<Object> getDependency(Class<?> dependencyToGet) {
        return Optional.of(dependencyHashMap.get(dependencyToGet));
    }

    public Integer numberOfDependencies() {
        return dependencyHashMap.size();
    }
}
