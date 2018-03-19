package tooling.tree;

import model.Dependency;
import model.DependencyReflectionRepresentation;
import model.ReflectionUtils;
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
    private final ReflectionUtils reflectionUtils;

    public CycleCheckingDependencyFactory(DependencyComponentFactory dependencyComponentFactoryComponent, CycleChecker cycleChecker, ReflectionUtils reflectionUtils) {
        super(dependencyComponentFactoryComponent);
        this.cycleChecker = cycleChecker;
        this.reflectionUtils = reflectionUtils;
    }

    @Override
    public Dependency createDependencyComponent(Class<?> dependencyClass, DependencyContextComponent dependencyContextService) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        cycleChecker.checkForCycle(dependencyClass, new DependencyReflectionRepresentation(dependencyClass, reflectionUtils));
        return super.createDependencyComponent(dependencyClass, dependencyContextService);
    }
}
