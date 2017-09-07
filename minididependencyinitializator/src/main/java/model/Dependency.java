package model;

import lombok.Getter;
import tooling.ReflectionInitializerTool;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by akivv on 5.9.2017.
 */
public class Dependency {
    private final Class<?> dependencyClass;
    @Getter
    private Object dependencyInstance;

    public Dependency(Class<?> dependencyClass) {
        this.dependencyClass = dependencyClass;

    }

    /**
     * Initializes an object from the given dependency class
     */
    public void initializeDependencyObject(ReflectionInitializerTool reflectionInitializer) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        this.dependencyInstance = reflectionInitializer.initialize(dependencyClass);
    }

    /**
     * Adds properties of this Dependency to the dependency context map
     *
     * @param dependencyContext
     */
    public void addDependencyToMap(DependencyContext dependencyContext) {
        dependencyContext.registerDependencyAttributes(dependencyClass, dependencyInstance);
    }
}
