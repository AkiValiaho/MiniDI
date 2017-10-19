package model;

import tooling.CyclicDependencyException;
import tooling.DependencyContextComponent;
import tooling.DependencyFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 26.9.2017.
 */
public class DependencyFactoryImpl implements DependencyFactory {
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        final Dependency dependency = new Dependency(dependencyClass, dependencyContextService, new DependencyReflectionRepresentation(dependencyClass));
        dependency.instantiate();
        return dependency;
    }
}
