package model;

import tooling.CyclicDependencyException;
import tooling.DependencyContextComponent;
import tooling.GenericDependencyComponentFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 26.9.2017.
 */
public class DependencyFactory extends GenericDependencyComponentFactory {
    @Override
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        super.createDependency(dependencyClass, dependencyContextService);
        final Dependency dependency = new Dependency(dependencyClass,
                dependencyContextService,
                dependencyReflectionRepresentation,
                new DependentParamsFactory().createDependentParams(dependencyClass, dependencyContextService));
        dependency.instantiate();
        return dependency;
    }
}
