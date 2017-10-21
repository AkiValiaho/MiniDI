package tooling;

import model.Dependency;

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
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        return dependencyComponentFactoryComponent.createDependency(dependencyClass, dependencyContextService);
    }
}
