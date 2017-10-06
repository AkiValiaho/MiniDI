package tooling.graph;


import tooling.CyclicDependencyException;

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

    abstract void addComponent(TreeComponent treeComponent, TreeComponent relative) throws CyclicDependencyException;

    public Class<?> getComponentClass() {
        return dependencyClass;
    }

    abstract List<TreeComponent> initComponent() throws CyclicDependencyException;
}
