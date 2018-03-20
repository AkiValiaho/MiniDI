package model.tree;

import model.Dependency;
import model.DependencyReflectionRepresentation;
import tooling.ReflectionToolSet;
import tooling.CyclicDependencyException;
import model.components.DependencyComponentFactory;
import model.components.DependencyComponentFactoryDecorator;
import model.components.DependencyContextComponent;

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
