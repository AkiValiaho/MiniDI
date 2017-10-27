package model;

import model.dummyClasses.ClassWithPriorityDependency;
import model.dummyClasses.PriorityDependency;
import org.junit.Before;
import org.junit.Test;
import tooling.DependencyContextService;
import tooling.ReflectionTestHelper;
import tooling.ReflectionTool;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 27.10.2017.
 */
//TODO
public class DependencyContextTest {
    private DependencyContext dependencyContext;

    @Before
    public void setUp() throws Exception {
        this.dependencyContext = new DependencyContext();
    }

    @Test
    public void addDependencyToMap() throws Exception {

    }

    @Test
    public void comesFirst() throws Exception {
        dependencyContext.logDependency(PriorityDependency.class);
        dependencyContext.logDependency(ClassWithPriorityDependency.class);
        dependencyContext.comesFirst(PriorityDependency.class, ClassWithPriorityDependency.class);
    }

    @Test
    public void registerDependencyAttributes() throws Exception {
    }

    @Test
    public void getDependency() throws Exception {
        final DependencyFactory dependencyFactory = new DependencyFactory();
        final DependencyContextService dependencyContextService = new DependencyContextService(new ReflectionTool(), dependencyContext, dependencyFactory);
        dependencyContext.addDependencyToMap(dependencyFactory.createDependency(ClassWithPriorityDependency.class, dependencyContextService));
        final Optional<Object> dependency = dependencyContext.getDependency(ClassWithPriorityDependency.class);
        assertTrue(dependency.isPresent());
    }

    @Test
    public void numberOfDependencies() throws Exception {
    }

    @Test
    public void logDependency() throws Exception {
        dependencyContext.logDependency(PriorityDependency.class);
        final List<Class<?>> instanceOrderList = (List<Class<?>>) new ReflectionTestHelper().getField(dependencyContext, "instanceOrderList");
        assertTrue(instanceOrderList.size() == 1);
    }

}