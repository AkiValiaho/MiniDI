package tooling;

import model.DependencyComponent;
import model.DependencyReflectionRepresentation;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 19.10.2017.
 */
public class GenericDependencyComponentFactory implements DependencyFactory<DependencyComponent> {
    protected DependencyReflectionRepresentation dependencyReflectionRepresentation;

    @Override
    public DependencyComponent createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(dependencyClass);
        return null;
    }
}
