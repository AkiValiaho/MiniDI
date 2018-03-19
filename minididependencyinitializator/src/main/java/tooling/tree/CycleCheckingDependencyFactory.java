package tooling.tree;

import model.Dependency;
import model.DependencyReflectionRepresentation;
import model.ReflectionToolSet;
import tooling.CyclicDependencyException;
import tooling.DependencyComponentFactory;
import tooling.DependencyComponentFactoryDecorator;
import tooling.DependencyContextComponent;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleCheckingDependencyFactory extends DependencyComponentFactoryDecorator {

    private final CycleChecker cycleChecker;
    private final ReflectionToolSet reflectionToolSet;

    public CycleCheckingDependencyFactory(DependencyComponentFactory dependencyComponentFactoryComponent, CycleChecker cycleChecker, ReflectionToolSet reflectionToolSet) {
        super(dependencyComponentFactoryComponent);
        this.cycleChecker = cycleChecker;
        this.reflectionToolSet = reflectionToolSet;
    }

    @Override
    public Dependency createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, ReflectionToolSet reflectionToolSet) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        cycleChecker.checkForCycle(dependencyClass, new DependencyReflectionRepresentation(dependencyClass, this.reflectionToolSet));
        return super.createDependencyComponent(dependencyClass, dependencyContextService, reflectionToolSet);
    }
}
