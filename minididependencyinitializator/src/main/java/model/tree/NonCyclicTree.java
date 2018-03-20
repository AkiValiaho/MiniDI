package model.tree;

import model.DependencyReflectionRepresentation;
import tooling.ReflectionToolSet;
import tooling.CyclicDependencyException;

/**
 * Created by Aki on 4.10.2017.
 */
class NonCyclicTree {
    private final Class<?> rootClass;
    private final ReflectionToolSet reflectionToolSet;
    private TreeNode rootNode;

    NonCyclicTree(Class<?> rootClass, ReflectionToolSet reflectionToolSet) {
        this.rootClass = rootClass;
        this.reflectionToolSet = reflectionToolSet;
    }

    void attemptToBuildTree() throws CyclicDependencyException {
        this.rootNode = new TreeNode(rootClass, rootClass, new DependencyReflectionRepresentation(rootClass, reflectionToolSet), reflectionToolSet);
    }
}
