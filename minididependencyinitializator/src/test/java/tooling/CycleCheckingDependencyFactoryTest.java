package tooling;

import model.*;
import model.dummyClasses.CyclicClassA;
import model.dummyClasses.CyclicClassTransitiveA;
import org.junit.Before;
import org.junit.Test;
import tooling.tree.CycleChecker;
import tooling.tree.CycleCheckingDependencyFactory;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleCheckingDependencyFactoryTest {
    private CycleCheckingDependencyFactory cycleCheckingDependencyFactory;
    private DependencyFactory dependencyFactoryComponent;
    private DependencyContextService dependencyContextComponent;
    private ReflectionTool reflectionTool;

    @Before
    public void setUp() throws Exception {
        dependencyFactoryComponent = new DependencyFactory();
        reflectionTool = new ReflectionTool();
        DependencyContext context = new DependencyContext();
        dependencyContextComponent = new DependencyContextService(reflectionTool, context, dependencyFactoryComponent);
        this.cycleCheckingDependencyFactory = new CycleCheckingDependencyFactory(dependencyFactoryComponent, new CycleChecker());
    }

    @Test(expected = CyclicDependencyException.class)
    public void createDependency_cyclicTransitiveDependency_shouldThrowException() throws Exception {
        final Dependency dependency = this.cycleCheckingDependencyFactory.createDependency(CyclicClassTransitiveA.class, dependencyContextComponent, new DependencyReflectionRepresentation(CyclicClassTransitiveA.class));
    }

    @Test(expected = CyclicDependencyException.class)
    public void createDependency_cyclicClass_shouldThrowException() throws Exception {
        final Dependency dependency = this.cycleCheckingDependencyFactory.createDependency(CyclicClassA.class, dependencyContextComponent, new DependencyReflectionRepresentation(CyclicClassA.class));
    }

}