package tooling.tree;

import model.Dependency;
import model.DependencyReflectionRepresentation;
import model.ReflectionToolSet;
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

    private final ReflectionToolSet reflectionToolSet;

    public PriorityCheckingDependencyFactory(DependencyComponentFactory<Dependency> dependencyFactory, ReflectionToolSet reflectionToolSet) {
        super(dependencyFactory);
        this.reflectionToolSet = reflectionToolSet;
    }

    @Override
    public Dependency createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, ReflectionToolSet reflectionToolSet) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        //Instantiation order
        Optional<Class<?>> priorityDependency = new DependencyReflectionRepresentation(dependencyClass, this.reflectionToolSet).getPriorityDependencyClass();
        if (priorityDependency.isPresent()) {
            final Class<?> priotyDependencyClass = priorityDependency.get();
            dependencyContextService.createDependenciesFromResource(priotyDependencyClass);
        }
        return super.createDependencyComponent(dependencyClass, dependencyContextService, reflectionToolSet);
    }

}
