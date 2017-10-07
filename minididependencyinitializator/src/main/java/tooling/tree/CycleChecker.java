package tooling.tree;

import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleChecker {
    void checkForCycle(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws CyclicDependencyException {
        new NonCyclicTree(dependencyClass)
                .attemptToBuildTree();
    }
}
