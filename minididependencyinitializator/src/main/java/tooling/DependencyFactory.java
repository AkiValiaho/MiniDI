package tooling;

import model.Dependency;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
public interface DependencyFactory {
    Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException;
}
