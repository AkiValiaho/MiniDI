package tooling.tree;

import model.DependencyReflectionRepresentation;
import model.ReflectionToolSet;
import tooling.CyclicDependencyException;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleChecker {
    private final ReflectionToolSet reflectionutils;

    public CycleChecker(ReflectionToolSet reflectionToolSet) {
        this.reflectionutils = reflectionToolSet;
    }
    void checkForCycle(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws CyclicDependencyException {
        new NonCyclicTree(dependencyClass, reflectionutils)
                .attemptToBuildTree();
    }
}
