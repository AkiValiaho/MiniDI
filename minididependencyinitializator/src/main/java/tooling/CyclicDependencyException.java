package tooling;

import tooling.graph.TreeComponent;

/**
 * Created by Aki on 4.10.2017.
 */
public class CyclicDependencyException extends Exception {
    public CyclicDependencyException(TreeComponent treeComponent, TreeComponent relative) {
        System.out.println("Cyclic dependency between " + treeComponent.getComponentClass() + " and " + relative.getComponentClass());
    }
}
