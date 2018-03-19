package tooling.tree;

import model.ReflectionUtils;
import model.dummyClasses.CyclicClassA;
import org.junit.Before;
import org.junit.Test;
import tooling.CyclicDependencyException;
import tooling.DummyTestClassWithDependency;

public class NonCyclicTreeTest {
    private NonCyclicTree nonCyclicTree;
    private ReflectionUtils reflectionutils;

    @Before
    public void setUp() throws Exception {
        this.reflectionutils = new ReflectionUtils();
        this.nonCyclicTree = new NonCyclicTree(CyclicClassA.class, reflectionutils);
    }

    @Test(expected = CyclicDependencyException.class)
    public void attemptToBuildTree_cyclicClass_shouldThrowException() throws Exception {
        nonCyclicTree.attemptToBuildTree();
    }

    @Test
    public void attemptToBuildTree_noncyclicClass_shouldReturnTree() throws CyclicDependencyException {
        this.nonCyclicTree = new NonCyclicTree(DummyTestClassWithDependency.class, reflectionutils);
        nonCyclicTree.attemptToBuildTree();
    }


}