package tooling;

/**
 * Created by Aki on 4.10.2017.
 */
public class CyclicDependencyException extends Exception {
    public CyclicDependencyException(Class<?> treeComponent, Class<?> relative) {
        System.out.println("Cyclic dependency between " + treeComponent.getName() + " and " + relative);
    }
}
