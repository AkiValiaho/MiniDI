package tooling.tree;

import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;

/**
 * Created by Aki on 4.10.2017.
 */
class NonCyclicTree {
    private final Class<?> rootClass;
    private TreeNode rootNode;

    NonCyclicTree(Class<?> rootClass) {
        this.rootClass = rootClass;
    }

    void attemptToBuildTree() throws CyclicDependencyException {
        this.rootNode = new TreeNode(rootClass, rootClass, new DependencyReflectionRepresentation(rootClass));
    }
}
