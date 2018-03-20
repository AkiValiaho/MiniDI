package tooling;

import model.*;
import model.dummyClasses.CyclicClassA;
import model.dummyClasses.CyclicClassTransitiveA;
import org.junit.Before;
import org.junit.Test;
import model.tree.CycleChecker;
import model.tree.CycleCheckingDependencyFactory;

/**
 * Created by Aki on 4.10.2017.
 */
public class CycleCheckingDependencyFactoryImplTest {
    private CycleCheckingDependencyFactory cycleCheckingDependencyFactory;
    private DependencyFactory dependencyFactoryImplComponent;
    private DependencyContextService dependencyContextComponent;
    private ReflectionTool reflectionTool;
    private ReflectionToolSet reflectionToolSet;

    @Before
    public void setUp() throws Exception {
        dependencyFactoryImplComponent = new DependencyFactory(reflectionToolSet);
        reflectionTool = new ReflectionTool();
        reflectionToolSet = new ReflectionUtils();
        DependencyContext context = new DependencyContext();
        dependencyContextComponent = new DependencyContextService(reflectionTool, context, dependencyFactoryImplComponent, reflectionToolSet);
        this.cycleCheckingDependencyFactory = new CycleCheckingDependencyFactory(dependencyFactoryImplComponent, new CycleChecker(reflectionToolSet), reflectionToolSet);
    }

    @Test(expected = CyclicDependencyException.class)
    public void createDependency_cyclicTransitiveDependency_shouldThrowException() throws Exception {
        final Dependency dependency = this.cycleCheckingDependencyFactory.createDependencyComponent(CyclicClassTransitiveA.class, dependencyContextComponent, reflectionToolSet);
    }

    @Test(expected = CyclicDependencyException.class)
    public void createDependency_cyclicClass_shouldThrowException() throws Exception {
        final Dependency dependency = this.cycleCheckingDependencyFactory.createDependencyComponent(CyclicClassA.class, dependencyContextComponent, reflectionToolSet);
    }

}