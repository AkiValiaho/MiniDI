package model;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 26.9.2017.
 */
public class DependencyFactory {
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextService dependencyContextService, ReflectionRepresentation reflectionRepresentation) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Dependency dependency = new Dependency(dependencyClass, dependencyContextService, reflectionRepresentation);
        dependency.instantiate();
        return dependency;
    }
}
