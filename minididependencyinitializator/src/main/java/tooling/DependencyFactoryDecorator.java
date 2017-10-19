package tooling;

import model.Dependency;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
public abstract class DependencyFactoryDecorator implements DependencyFactory {
    private final DependencyFactory dependencyFactoryComponent;

    public DependencyFactoryDecorator(DependencyFactory dependencyFactoryComponent) {
        this.dependencyFactoryComponent = dependencyFactoryComponent;
    }

    @Override
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        return dependencyFactoryComponent.createDependency(dependencyClass, dependencyContextService);
    }
}
