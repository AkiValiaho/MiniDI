package tooling;

import model.CyclicClassA;
import model.Dependency;
import model.DependencyContext;
import model.DependencyReflectionRepresentation;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * TODO
 * Created by Aki on 4.10.2017.
 */
public class CycleCheckingDependencyFactoryTest {
    private CycleCheckingDependencyFactory cycleCheckingDependencyFactory;
    private DependencyFactory dependencyFactoryComponent;
    private DependencyContextService dependencyContextComponent;
    private ReflectionTool reflectionTool;

    @Before
    public void setUp() throws Exception {
        //TODO looks horrific, the Api's got too much stuff here
        dependencyFactoryComponent = new DependencyFactory();
        reflectionTool = new ReflectionTool();
        DependencyContext context = new DependencyContext();
        dependencyContextComponent = new DependencyContextService(reflectionTool, context, dependencyFactoryComponent);
        this.cycleCheckingDependencyFactory = new CycleCheckingDependencyFactory(dependencyFactoryComponent, new CycleChecker());
    }

    @Test(expected = CyclicDependencyException.class)
    @Ignore
    public void createDependency_cyclicClass_shouldThrowException() throws Exception {
        //TODO
        final Dependency dependency = this.cycleCheckingDependencyFactory.createDependency(CyclicClassA.class, dependencyContextComponent, new DependencyReflectionRepresentation(CyclicClassA.class));
    }

}