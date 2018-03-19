package tooling;

import model.DependencyComponent;
import model.DependencyReflectionRepresentation;
import model.ReflectionToolSet;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 19.10.2017.
 */
public class GenericDependencyComponentFactory implements DependencyComponentFactory<DependencyComponent> {
    protected ReflectionToolSet reflectionToolSet;
    protected DependencyReflectionRepresentation dependencyReflectionRepresentation;

    public GenericDependencyComponentFactory(ReflectionToolSet reflectionToolSet) {
        this.reflectionToolSet = reflectionToolSet;
    }

    @Override
    public DependencyComponent createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, ReflectionToolSet reflectionToolSet) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(dependencyClass, this.reflectionToolSet);
        return null;
    }
}
