package tooling.tree;

import model.DependencyReflectionRepresentation;
import tooling.ReflectionToolSet;
import tooling.CyclicDependencyException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Aki on 4.10.2017.
 */
class TreeNode {
    private final ReflectionToolSet reflectionToolSet;
    List<TreeNode> dependents;
    private Class<?> rootNode;
    private Class<?> nodeClass;


    TreeNode(Class<?> rootNode, Class<?> nodeClass, DependencyReflectionRepresentation dependencyReflectionRepresentation, ReflectionToolSet reflectionToolSet) throws CyclicDependencyException {
        this.rootNode = rootNode;
        this.nodeClass = nodeClass;
        this.dependents = new ArrayList<>();
        this.reflectionToolSet = reflectionToolSet;
        dependents.addAll(getDependentNodes(dependencyReflectionRepresentation));
    }

    private List<TreeNode> getDependentNodes(DependencyReflectionRepresentation dependencyReflectionRepresentation) throws CyclicDependencyException {
        List<TreeNode> dependents = new ArrayList<>();
        getDependentsAsNodes(dependencyReflectionRepresentation, dependents);
        return dependents;
    }

    private void getDependentsAsNodes(DependencyReflectionRepresentation dependencyReflectionRepresentation, List<TreeNode> dependents) throws CyclicDependencyException {
        final Class<?>[] dependentParamsFromFields = dependencyReflectionRepresentation.getDependentParamsFromFields();
        final Class<?>[] paramTypesFromArgsConstructor = dependencyReflectionRepresentation.getParamTypesFromArgsConstructor();
        final Class<?>[] classes = Stream.concat(nullSafeStream(dependentParamsFromFields),
                nullSafeStream(paramTypesFromArgsConstructor))
                .toArray(Class<?>[]::new);
        addNodes(classes, dependents);
    }

    private Stream<Class<?>> nullSafeStream(Class<?>[] array) {
        if (array == null) {
            Class<?>[] classes = new Class[0];
            return Arrays.stream(classes);
        }
        return Arrays.stream(array);
    }


    private void addNodes(Class<?>[] nodes, List<TreeNode> dependents) throws CyclicDependencyException {
        if (nodes != null) {
            for (Class<?> node : nodes) {
                addNode(dependents, node);
            }
        }
    }

    private void addNode(List<TreeNode> dependents, Class<?> aClass) throws CyclicDependencyException {
        checkCycle(rootNode, aClass);
        final TreeNode treeNode = new TreeNode(rootNode, aClass, new DependencyReflectionRepresentation(aClass, reflectionToolSet), reflectionToolSet);
        dependents.add(treeNode);
    }

    private void checkCycle(Class<?> rootNode, Class<?> aClass) throws CyclicDependencyException {
        if (rootNode.equals(aClass)) {
            throw new CyclicDependencyException(rootNode, nodeClass);
        }
    }
}
