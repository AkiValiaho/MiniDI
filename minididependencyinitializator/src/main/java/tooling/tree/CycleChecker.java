package tooling.tree;

import model.DependencyReflectionRepresentation;
import model.ReflectionUtils;
import tooling.CyclicDependencyException;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleChecker {
    private final ReflectionUtils reflectionutils;

    public CycleChecker(ReflectionUtils reflectionUtils) {
        this.reflectionutils = reflectionUtils;
    }
    void checkForCycle(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws CyclicDependencyException {
        new NonCyclicTree(dependencyClass, reflectionutils)
                .attemptToBuildTree();
    }
}
