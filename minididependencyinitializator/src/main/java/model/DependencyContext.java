package model;

import java.util.*;

/**
 * Holds a map that contains all the resolved dependencies
 * Created by akivv on 5.9.2017.
 */
public class DependencyContext {

    private Map<Class<?>, Object> dependencyHashMap;
    private List<Class<?>> instanceOrderList;

    public DependencyContext() {
        this.dependencyHashMap = new HashMap<>();
        this.instanceOrderList = new ArrayList<>();
    }

    public void addDependencyToMap(Dependency dependency) {
        dependency.addDependencyToMap(this);
    }

    public Boolean comesFirst(Class<?> first, Class<?> second) {
        for (Class<?> aClass : instanceOrderList) {
            if (aClass.equals(first)) {
                return true;
            }
            if (aClass.equals(second)) {
                return false;
            }
        }
        return false;
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

    public void logDependency(Class<?> priotyDependencyClass) {
        instanceOrderList.add(priotyDependencyClass);
    }
}
