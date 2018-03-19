package tooling.tree;

import model.Dependency;
import model.DependencyReflectionRepresentation;
import model.ReflectionUtils;
import tooling.CyclicDependencyException;
import tooling.DependencyComponentFactory;
import tooling.DependencyComponentFactoryDecorator;
import tooling.DependencyContextComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * Created by Aki on 21.10.2017.
 */
public class PriorityCheckingDependencyFactory extends DependencyComponentFactoryDecorator {

    private final ReflectionUtils reflectionUtils;

    public PriorityCheckingDependencyFactory(DependencyComponentFactory<Dependency> dependencyFactory, ReflectionUtils reflectionUtils) {
        super(dependencyFactory);
        this.reflectionUtils = reflectionUtils;
    }

    @Override
    public Dependency createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        //Instantiation order
        Optional<Class<?>> priorityDependency = new DependencyReflectionRepresentation(dependencyClass, reflectionUtils).getPriorityDependencyClass();
        if (priorityDependency.isPresent()) {
            final Class<?> priotyDependencyClass = priorityDependency.get();
            dependencyContextService.createDependenciesFromResource(priotyDependencyClass);
        }
        return super.createDependencyComponent(dependencyClass, dependencyContextService);
    }

}
