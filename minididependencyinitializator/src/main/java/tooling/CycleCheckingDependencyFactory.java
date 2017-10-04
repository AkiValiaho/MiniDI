package tooling;

import model.Dependency;
import model.DependencyReflectionRepresentation;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
class CycleCheckingDependencyFactory extends DependencyFactoryDecorator {

    private final CycleChecker cycleChecker;

    CycleCheckingDependencyFactory(DependencyFactoryComponent dependencyFactoryComponent, CycleChecker cycleChecker) {
        super(dependencyFactoryComponent);
        this.cycleChecker = cycleChecker;
    }

    @Override
    public Dependency createDependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        cycleChecker.checkForCycle(dependencyClass, dependencyReflectionRepresentation);
        return super.createDependency(dependencyClass, dependencyContextService, dependencyReflectionRepresentation);
    }
}
