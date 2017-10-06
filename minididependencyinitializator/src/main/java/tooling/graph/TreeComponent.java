package tooling.graph;


import tooling.CyclicDependencyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Aki on 4.10.2017.
 */
public abstract class TreeComponent {
    private final Class<?> dependencyClass;
    Map<TreeComponent, List<TreeComponent>> relativeComponents;

    TreeComponent(Map<TreeComponent, List<TreeComponent>> relativeComponents, Class<?> dependencyClass) {
        this.relativeComponents = relativeComponents;
        this.dependencyClass = dependencyClass;
    }

    @Override
    public int hashCode() {
        return dependencyClass.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TreeComponent) {
            return hashCode() == obj.hashCode();
        }
        return false;
    }

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

    public Class<?> getComponentClass() {
        return dependencyClass;
    }

    ;

    abstract List<TreeComponent> initComponent() throws CyclicDependencyException;
}
