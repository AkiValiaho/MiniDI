package tooling;

import model.DependencyComponent;
import model.DependencyReflectionRepresentation;
import model.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 19.10.2017.
 */
public class GenericDependencyComponentFactory implements DependencyComponentFactory<DependencyComponent> {
    protected ReflectionUtils reflectionUtils;
    protected DependencyReflectionRepresentation dependencyReflectionRepresentation;

    public GenericDependencyComponentFactory(ReflectionUtils reflectionUtils) {
        this.reflectionUtils = reflectionUtils;
    }

    @Override
    public DependencyComponent createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(dependencyClass, reflectionUtils);
        return null;
    }
}
