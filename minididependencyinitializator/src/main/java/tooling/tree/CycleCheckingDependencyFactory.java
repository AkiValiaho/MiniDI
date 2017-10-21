package tooling.tree;

import model.Dependency;
import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;
import tooling.DependencyContextComponent;
import tooling.DependencyComponentFactory;
import tooling.DependencyComponentFactoryDecorator;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleCheckingDependencyFactory extends DependencyComponentFactoryDecorator {

    private final CycleChecker cycleChecker;

    public CycleCheckingDependencyFactory(DependencyComponentFactory dependencyComponentFactoryComponent, CycleChecker cycleChecker) {
        super(dependencyComponentFactoryComponent);
        this.cycleChecker = cycleChecker;
    }

    @Override
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        cycleChecker.checkForCycle(dependencyClass, new DependencyReflectionRepresentation(dependencyClass));
        return super.createDependency(dependencyClass, dependencyContextService);
    }
}
