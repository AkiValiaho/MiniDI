package model;

import org.junit.Before;
import org.junit.Test;
import tooling.*;

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
        final DependencyFactoryImpl dependencyFactoryImpl = new DependencyFactoryImpl();
        this.dependencyContextService = new DependencyContextService(reflectionTool, dependencyContext, dependencyFactoryImpl);
    }

    @Test
    public void isLeafParameter_onlyAutowiredConstructor_shouldReturnTrue() throws Exception {
        this.dependency = createDependency(DummyTestClass.class);
        assertTrue(dependency.isLeafParameter());
    }

    private Dependency createDependency(Class<?> argumentClass) throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        return new DependencyFactoryImpl().createDependency(argumentClass, dependencyContextService);
    }

    @Test
    public void isLeafParameter_onlyFieldInjections_shouldReturnFalse() throws Exception {
        this.dependency = createDependency(ClassWithInjectionField.class);
        assertNotALeafParameter();
    }

    private void assertNotALeafParameter() {
        assertTrue(!dependency.isLeafParameter());
    }

    @Test
    public void instantiateDependentParameters_onlyFieldInjections_shouldReturnTrue() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException, CyclicDependencyException {
        Dependency dependency = new DependencyFactoryImpl().createDependency(ClassWithInjectionField.class, dependencyContextService);
        assertTrue(dependency.instantiateDependentParameters());
        final Object dependentParameters = new ReflectionTestHelper().getField(dependency, "dependentParameters");
        assertTrue(((DependentParams) dependentParameters).getFieldInjectedInstances().length == 1);
    }

}