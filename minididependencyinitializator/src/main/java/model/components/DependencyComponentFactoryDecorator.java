package model.components;

import model.Dependency;
import model.components.DependencyComponentFactory;
import model.components.DependencyContextComponent;
import tooling.CyclicDependencyException;
import tooling.ReflectionToolSet;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
public abstract class DependencyComponentFactoryDecorator implements DependencyComponentFactory<Dependency> {
    private final DependencyComponentFactory<Dependency> dependencyComponentFactoryComponent;

    public DependencyComponentFactoryDecorator(DependencyComponentFactory<Dependency> dependencyComponentFactoryComponent) {
        this.dependencyComponentFactoryComponent = dependencyComponentFactoryComponent;
    }

    @Override
    public Dependency createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, ReflectionToolSet reflectionToolSet) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        return dependencyComponentFactoryComponent.createDependencyComponent(dependencyClass, dependencyContextService, reflectionToolSet);
    }
}
