package tooling.tree;

import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aki on 4.10.2017.
 */
public class TreeNode {
    List<TreeNode> dependents;
    private Class<?> rootNode;
    private Class<?> nodeClass;


    TreeNode(Class<?> rootNode, Class<?> nodeClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) throws CyclicDependencyException {
        this.rootNode = rootNode;
        this.nodeClass = nodeClass;
        this.dependents = new ArrayList<>();
        dependents.addAll(getDependentNodes(dependencyReflectionRepresentation));
    }

    private List<TreeNode> getDependentNodes(DependencyReflectionRepresentation dependencyReflectionRepresentation) throws CyclicDependencyException {
        List<TreeNode> dependents = new ArrayList<>();
        getFieldDependentsAsNodes(dependencyReflectionRepresentation, dependents);
        getArgsDependentsAsNodes(dependencyReflectionRepresentation, dependents);
        return dependents;
    }

    private void getFieldDependentsAsNodes(DependencyReflectionRepresentation dependencyReflectionRepresentation, List<TreeNode> dependents) throws CyclicDependencyException {
        if (dependencyReflectionRepresentation.getDependentParamsFromFields() != null) {
            for (Class<?> aClass : dependencyReflectionRepresentation.getDependentParamsFromFields()) {
                checkCycle(rootNode, aClass);
                final TreeNode treeNode = new TreeNode(rootNode, aClass, new DependencyReflectionRepresentation(aClass));
                dependents.add(treeNode);
            }
        }
    }

    private void getArgsDependentsAsNodes(DependencyReflectionRepresentation dependencyReflectionRepresentation, List<TreeNode> dependents) throws CyclicDependencyException {
        if (dependencyReflectionRepresentation.getParamTypesFromArgsConstructor() != null) {
            for (Class<?> aClass : dependencyReflectionRepresentation
                    .getParamTypesFromArgsConstructor()) {
                checkCycle(rootNode, aClass);
                final TreeNode treeNode = new TreeNode(rootNode, aClass, new DependencyReflectionRepresentation(aClass));
                dependents.add(treeNode);
            }
        }
    }

    private void checkCycle(Class<?> rootNode, Class<?> aClass) throws CyclicDependencyException {
        if (rootNode.equals(aClass)) {
            throw new CyclicDependencyException(rootNode, nodeClass);
        }
    }
}
