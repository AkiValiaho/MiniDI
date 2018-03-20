package model.components;

import model.DependencyReflectionRepresentation;
import model.components.DependencyComponent;
import model.components.DependencyComponentFactory;
import model.components.DependencyContextComponent;
import tooling.CyclicDependencyException;
import tooling.ReflectionToolSet;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 19.10.2017.
 */
public class GenericDependencyComponentFactory implements DependencyComponentFactory<DependencyComponent> {
    protected ReflectionToolSet reflectionToolSet;
    protected DependencyReflectionRepresentation dependencyReflectionRepresentation;

    protected GenericDependencyComponentFactory(ReflectionToolSet reflectionToolSet) {
        this.reflectionToolSet = reflectionToolSet;
    }

    @Override
    public DependencyComponent createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, ReflectionToolSet reflectionToolSet) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(dependencyClass, this.reflectionToolSet);
        return null;
    }
}
