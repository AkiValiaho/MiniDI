package tooling.graph;

import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;

import java.util.HashMap;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Created by Aki on 4.10.2017.
 */
class NonCyclicTree extends TreeComponent {
    private final Class<?> dependencyClass;
    private final DependencyReflectionRepresentation dependencyReflectionRepresentation;

    NonCyclicTree(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) {
        super(new HashMap<>(), dependencyClass);
        this.dependencyClass = dependencyClass;
        this.dependencyReflectionRepresentation = dependencyReflectionRepresentation;
    }

    private NonCyclicTree initTree() throws CyclicDependencyException {
        List<TreeComponent> nodes = new TreeNode(dependencyClass, dependencyReflectionRepresentation, this)
                .initComponent();
        return this;
    }


    @Override
    List<TreeComponent> initComponent() throws CyclicDependencyException {
        initTree();
        return singletonList(this);
    }
}
