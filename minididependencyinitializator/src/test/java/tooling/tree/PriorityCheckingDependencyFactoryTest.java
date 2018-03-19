package tooling.tree;

import model.Dependency;
import model.DependencyContext;
import model.DependencyFactory;
import model.ReflectionUtils;
import model.dummyClasses.ClassWithPriorityDependency;
import model.dummyClasses.PriorityDependency;
import org.junit.Before;
import org.junit.Test;
import tooling.DependencyContextService;
import tooling.ReflectionTool;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 27.10.2017.
 */
public class PriorityCheckingDependencyFactoryTest {
    private PriorityCheckingDependencyFactory priorityCheckingDependencyFactory;
    private ReflectionUtils reflectionUtils;

    @Before
    public void setUp() throws Exception {
        this.reflectionUtils = new ReflectionUtils();
        this.priorityCheckingDependencyFactory = new PriorityCheckingDependencyFactory(new CycleCheckingDependencyFactory(new DependencyFactory(reflectionUtils), new CycleChecker(reflectionUtils), reflectionUtils), reflectionUtils);
    }

    @Test
    public void createDependency() throws Exception {
        DependencyContext dependencyContext = new DependencyContext();
        final DependencyContextService dependencyContextService = new DependencyContextService(new ReflectionTool(), dependencyContext, this.priorityCheckingDependencyFactory);
        final Dependency dependency = this.priorityCheckingDependencyFactory.createDependencyComponent(ClassWithPriorityDependency.class, dependencyContextService);
        assertTrue(dependencyContext.comesFirst(PriorityDependency.class, ClassWithPriorityDependency.class));

    }

}