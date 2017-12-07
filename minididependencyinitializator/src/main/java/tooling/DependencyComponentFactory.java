package tooling;

import model.DependencyComponent;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
public interface DependencyComponentFactory<T extends DependencyComponent> {
    T createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException;
}
