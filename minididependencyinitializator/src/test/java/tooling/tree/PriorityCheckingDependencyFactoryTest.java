package tooling.tree;

import model.*;
import model.dummyClasses.ClassWithPriorityDependency;
import model.dummyClasses.PriorityDependency;
import org.junit.Before;
import org.junit.Test;
import tooling.DependencyContextService;
import tooling.ReflectionTool;
import tooling.ReflectionToolSet;
import tooling.ReflectionUtils;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 27.10.2017.
 */
public class PriorityCheckingDependencyFactoryTest {
    private PriorityCheckingDependencyFactory priorityCheckingDependencyFactory;
    private ReflectionToolSet reflectionToolSet;

    @Before
    public void setUp() throws Exception {
        this.reflectionToolSet = new ReflectionUtils();
        this.priorityCheckingDependencyFactory = new PriorityCheckingDependencyFactory(new CycleCheckingDependencyFactory(new DependencyFactory(reflectionToolSet), new CycleChecker(reflectionToolSet), reflectionToolSet), reflectionToolSet);
    }

    @Test
    public void createDependency() throws Exception {
        DependencyContext dependencyContext = new DependencyContext();
        final DependencyContextService dependencyContextService = new DependencyContextService(new ReflectionTool(), dependencyContext, this.priorityCheckingDependencyFactory, reflectionToolSet);
        final Dependency dependency = this.priorityCheckingDependencyFactory.createDependencyComponent(ClassWithPriorityDependency.class, dependencyContextService, reflectionToolSet);
        assertTrue(dependencyContext.comesFirst(PriorityDependency.class, ClassWithPriorityDependency.class));

    }

}