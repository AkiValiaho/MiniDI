package tooling;

import model.Dependency;
import model.DependencyReflectionRepresentation;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
abstract class DependencyFactoryDecorator implements DependencyFactoryComponent {
    private final DependencyFactoryComponent dependencyFactoryComponent;

    public DependencyFactoryDecorator(DependencyFactoryComponent dependencyFactoryComponent) {
        this.dependencyFactoryComponent = dependencyFactoryComponent;
    }

    @Override
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return dependencyFactoryComponent.createDependency(dependencyClass, dependencyContextService, dependencyReflectionRepresentation);
    }
}
