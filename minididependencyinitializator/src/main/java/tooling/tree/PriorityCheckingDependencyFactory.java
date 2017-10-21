package tooling.tree;

import model.Dependency;
import tooling.CyclicDependencyException;
import tooling.DependencyComponentFactory;
import tooling.DependencyComponentFactoryDecorator;
import tooling.DependencyContextComponent;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 21.10.2017.
 */
public class PriorityCheckingDependencyFactory extends DependencyComponentFactoryDecorator {
    private final PriorityChecker priorityChecker;

    public PriorityCheckingDependencyFactory(DependencyComponentFactory<Dependency> dependencyComponentFactoryComponent, PriorityChecker priorityChecker) {
        super(dependencyComponentFactoryComponent);
        this.priorityChecker = priorityChecker;
    }

    @Override
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        //Instantiation order
        Class<?> priorityDependency = priorityChecker.getPriorityDependency(dependencyClass);
        if (priorityDependency != null) {
            dependencyContextService.createDependenciesFromResource(priorityDependency);
        }
        return super.createDependency(dependencyClass, dependencyContextService);
    }

}
