package model;

import tooling.CyclicDependencyException;
import tooling.DependencyContextComponent;
import tooling.GenericDependencyComponentFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 19.10.2017.
 */
class DependentParamsFactory extends GenericDependencyComponentFactory {
    DependentParamsFactory(ReflectionUtils reflectionUtils) {
        super(reflectionUtils);
    }

    DependentParams createDependentParams(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        super.createDependencyComponent(dependencyClass, dependencyContextService);
        return new DependentParams().getDependentParamsForClass(dependencyReflectionRepresentation, dependencyContextService);
    }
}
