package tooling.tree;

import model.dummyClasses.CyclicClassA;
import org.junit.Before;
import org.junit.Test;
import tooling.CyclicDependencyException;
import tooling.DummyTestClassWithDependency;

public class NonCyclicTreeTest {
    private NonCyclicTree nonCyclicTree;

    @Before
    public void setUp() throws Exception {
        this.nonCyclicTree = new NonCyclicTree(CyclicClassA.class);
    }

    @Test(expected = CyclicDependencyException.class)
    public void attemptToBuildTree_cyclicClass_shouldThrowException() throws Exception {
        nonCyclicTree.attemptToBuildTree();
    }

    @Test
    public void attemptToBuildTree_noncyclicClass_shouldReturnTree() throws CyclicDependencyException {
        this.nonCyclicTree = new NonCyclicTree(DummyTestClassWithDependency.class);
        nonCyclicTree.attemptToBuildTree();
    }


}