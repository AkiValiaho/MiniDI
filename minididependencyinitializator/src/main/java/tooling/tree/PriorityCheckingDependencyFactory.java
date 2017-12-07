package tooling.tree;

import model.Dependency;
import model.DependencyReflectionRepresentation;
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

    public PriorityCheckingDependencyFactory(DependencyComponentFactory<Dependency> dependencyFactory) {
        super(dependencyFactory);
    }

    @Override
    public Dependency createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        //Instantiation order
        Optional<Class<?>> priorityDependency = new DependencyReflectionRepresentation(dependencyClass).getPriorityDependencyClass();
        if (priorityDependency.isPresent()) {
            final Class<?> priotyDependencyClass = priorityDependency.get();
            dependencyContextService.createDependenciesFromResource(priotyDependencyClass);
        }
        return super.createDependencyComponent(dependencyClass, dependencyContextService);
    }

}
