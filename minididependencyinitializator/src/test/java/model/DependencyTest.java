package model;

import org.junit.Before;
import org.junit.Test;
import tooling.ClassWithInjectionField;
import tooling.DummyTestClass;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertTrue;

public class DependencyTest {

    private DependencyContextService dependencyContextService;
    private Dependency dependency;
    private ReflectionTool reflectionTool;

    @Before
    public void before() {
        reflectionTool = new ReflectionTool();
        final DependencyContext dependencyContext = new DependencyContext();
        final DependencyFactory dependencyFactory = new DependencyFactory();
        this.dependencyContextService = new DependencyContextService(reflectionTool,dependencyContext,dependencyFactory);
    }

    @Test
    public void isLeafParameter_onlyAutowiredConstructor_shouldReturnTrue() throws Exception {
        this.dependency = new Dependency(DummyTestClass.class, dependencyContextService, new ReflectionRepresentation(DummyTestClass.class, reflectionTool));
        assertTrue(dependency.isLeafParameter());
    }

    @Test
    public void isLeafParameter_onlyFieldInjections_shouldReturnFalse() throws Exception {
        this.dependency = new Dependency(ClassWithInjectionField.class, dependencyContextService,new ReflectionRepresentation(ClassWithInjectionField.class, reflectionTool));
        assertNotALeafParameter();
    }

    private void assertNotALeafParameter() {
        assertTrue(!dependency.isLeafParameter());
    }

    @Test
    public void instantiateDependentParameters_onlyFieldInjections_shouldReturnTrue() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Dependency dependency = new Dependency(ClassWithInjectionField.class, dependencyContextService, new ReflectionRepresentation(ClassWithInjectionField.class, reflectionTool));
        assertTrue(dependency.instantiateDependentParameters());
        assertTrue(dependency.numberOfDependentParameters() == 1);
    }

}