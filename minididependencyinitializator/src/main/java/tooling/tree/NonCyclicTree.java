package tooling.tree;

import model.DependencyReflectionRepresentation;
import model.ReflectionUtils;
import tooling.CyclicDependencyException;

/**
 * Created by Aki on 4.10.2017.
 */
class NonCyclicTree {
    private final Class<?> rootClass;
    private final ReflectionUtils reflectionUtils;
    private TreeNode rootNode;

    NonCyclicTree(Class<?> rootClass, ReflectionUtils reflectionUtils) {
        this.rootClass = rootClass;
        this.reflectionUtils = reflectionUtils;
    }

    void attemptToBuildTree() throws CyclicDependencyException {
        this.rootNode = new TreeNode(rootClass, rootClass, new DependencyReflectionRepresentation(rootClass, reflectionUtils), reflectionUtils);
    }
}
