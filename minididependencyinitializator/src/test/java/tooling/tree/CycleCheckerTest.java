package tooling.tree;

import model.DependencyReflectionRepresentation;
import model.dummyClasses.CyclicClassA;
import org.junit.Before;
import org.junit.Test;
import tooling.CyclicDependencyException;

public class CycleCheckerTest {
    private CycleChecker cycleChecker;

    @Before
    public void setUp() throws Exception {
        this.cycleChecker = new CycleChecker();
    }

    @Test(expected = CyclicDependencyException.class)
    public void checkForCycle_cyclicClass_shouldThrowException() throws Exception {
        cycleChecker.checkForCycle(CyclicClassA.class, new DependencyReflectionRepresentation(CyclicClassA.class));
    }

}