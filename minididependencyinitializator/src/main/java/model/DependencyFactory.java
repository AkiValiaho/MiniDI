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
    public Dependency createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        super.createDependencyComponent(dependencyClass, dependencyContextService);
        final Dependency dependency = new Dependency(dependencyClass,
                dependencyContextService,
                dependencyReflectionRepresentation,
                new DependentParamsFactory().createDependentParams(dependencyClass, dependencyContextService));
        callInstantiationLogic(dependencyClass, dependencyContextService, dependency);
        return dependency;
    }

    private void callInstantiationLogic(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, Dependency dependency) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        dependency.instantiate();
        dependencyContextService.logDependencyInstantiation(dependencyClass);
        dependency.callPostConstructIfPresent();
    }
}
