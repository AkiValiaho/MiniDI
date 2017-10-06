package tooling.graph;

import model.DependencyReflectionRepresentation;
import tooling.CyclicDependencyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Aki on 4.10.2017.
 */
class TreeNode extends TreeComponent {
    private final DependencyReflectionRepresentation dependencyReflectionRepresentation;
    private final TreeComponent rootTree;

    TreeNode(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation, TreeComponent rootTree) {
        super(new HashMap<>(), dependencyClass);
        this.dependencyReflectionRepresentation = dependencyReflectionRepresentation;
        this.rootTree = rootTree;
    }

    /**
     * Double-dispatches the addComponent call to the given root tree (validation implementation might vary)
     *
     * @param treeComponent
     * @param relative
     * @throws CyclicDependencyException
     */
    @Override
    void addComponent(TreeComponent treeComponent, TreeComponent relative) throws CyclicDependencyException {
        rootTree.addComponent(treeComponent, relative);
    }

    @Override
    List<TreeComponent> initComponent() throws CyclicDependencyException {
        final Class<?>[] paramTypesFromArgsConstructor = dependencyReflectionRepresentation.getParamTypesFromArgsConstructor();
        final Class<?>[] dependentParamsFromFields = dependencyReflectionRepresentation.getDependentParamsFromFields();
        return listOfRelativeNodes(paramTypesFromArgsConstructor, dependentParamsFromFields);
    }

    private List<TreeComponent> listOfRelativeNodes(Class<?>[] paramTypesFromArgsConstructor, Class<?>[] dependentParamsFromFields) throws CyclicDependencyException {
        final List<TreeComponent> argsNodes = createNodes(paramTypesFromArgsConstructor);
        final List<TreeComponent> fieldNodes = createNodes(dependentParamsFromFields);
        if (argsNodes.size() == 0 && fieldNodes.size() == 0) {
            return Collections.emptyList();
        }

        return combineComponents(argsNodes, fieldNodes);
    }

    private List<TreeComponent> combineComponents(List<TreeComponent> argsNodes, List<TreeComponent> fieldNodes) {
        final List<TreeComponent> objects = new ArrayList<>(argsNodes);
        objects.addAll(fieldNodes);
        return objects;
    }

    private List<TreeComponent> createNodes(Class<?>[] dependentParams) throws CyclicDependencyException {
        if (dependentParams == null) {
            return new ArrayList<>();
        }
        List<TreeComponent> nodes = new ArrayList<>();
        for (Class<?> dependentParam : dependentParams) {
            nodes.add(constructTreeNode(dependentParam));
        }
        return nodes;
    }

    private TreeComponent constructTreeNode(Class<?> aClass) throws CyclicDependencyException {
        final TreeComponent treeNode = new TreeNode(aClass, new DependencyReflectionRepresentation(aClass), rootTree);
        addComponent(rootTree, treeNode);
        treeNode.initComponent();
        return treeNode;
    }
}
