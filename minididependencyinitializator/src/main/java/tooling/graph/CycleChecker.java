package tooling.graph;

import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleChecker {
    void checkForCycle(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws CyclicDependencyException {
        //TODO Find if it's possible to reuse the tree calculated in a particular dependency again to make calculations faster
        new NonCyclicTree(dependencyClass, dependencyReflectionRepresentation)
                .initComponent();
    }
}
