package tooling;

import model.Dependency;
import model.DependencyReflectionRepresentation;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
class CycleCheckingDependencyFactory extends DependencyFactoryDecorator{

    CycleCheckingDependencyFactory(DependencyFactoryComponent dependencyFactoryComponent) {
        super(dependencyFactoryComponent);
    }

    @Override
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        //TODO Check for cycles here
        return super.createDependency(dependencyClass, dependencyContextService, dependencyReflectionRepresentation);
    }
}
