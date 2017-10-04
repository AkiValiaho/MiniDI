package tooling;

import model.Dependency;
import model.DependencyReflectionRepresentation;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 26.9.2017.
 */
public class DependencyFactory implements DependencyFactoryComponent {
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        final Dependency dependency = new Dependency(dependencyClass, dependencyContextService, dependencyReflectionRepresentation);
        dependency.instantiate();
        return dependency;
    }
}
