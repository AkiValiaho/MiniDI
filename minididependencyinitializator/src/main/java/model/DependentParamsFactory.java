package model;

import tooling.CyclicDependencyException;
import tooling.ReflectionToolSet;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 19.10.2017.
 */
class DependentParamsFactory extends GenericDependencyComponentFactory {
    DependentParamsFactory(ReflectionToolSet reflectionToolSet) {
        super(reflectionToolSet);
    }

    DependentParams createDependentParams(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        super.createDependencyComponent(dependencyClass, dependencyContextService, reflectionToolSet);
        return new DependentParams().getDependentParamsForClass(dependencyReflectionRepresentation, dependencyContextService);
    }
}
