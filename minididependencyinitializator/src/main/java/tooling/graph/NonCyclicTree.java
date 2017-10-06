package tooling.graph;

import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;

import java.util.ArrayList;
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


    /**
     * AddComponent implementation that checks for cycles while adding a new tree component
     *
     * @param treeComponent
     * @param relative
     * @throws CyclicDependencyException
     */
    @Override
    void addComponent(TreeComponent treeComponent, TreeComponent relative) throws CyclicDependencyException {
        if (oldEntryPresent(treeComponent, relative)) {
            return;
        }
        createNewEntry(treeComponent, relative);
    }

    private boolean oldEntryPresent(TreeComponent treeComponent, TreeComponent relative) throws CyclicDependencyException {
        if (relativeComponents.containsKey(treeComponent)) {
            final List<TreeComponent> treeComponents = relativeComponents.get(treeComponent);
            if (treeComponents.stream().anyMatch(relative::equals)) {
                throw new CyclicDependencyException(treeComponent, relative);
            } else {
                treeComponents.add(relative);
                return true;
            }
        }
        return false;
    }

    private void createNewEntry(TreeComponent treeComponent, TreeComponent relative) {
        final ArrayList<TreeComponent> value = new ArrayList<>();
        value.add(relative);
        relativeComponents.put(treeComponent, value);
    }

    @Override
    List<TreeComponent> initComponent() throws CyclicDependencyException {
        initTree();
        return singletonList(this);
    }
}
