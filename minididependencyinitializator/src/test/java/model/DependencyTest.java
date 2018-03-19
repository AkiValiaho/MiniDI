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
    private ReflectionUtils reflectionutils;

    @Before
    public void before() {
        reflectionTool = new ReflectionTool();
        reflectionutils = new ReflectionUtils();
        final DependencyContext dependencyContext = new DependencyContext();
        final DependencyFactory dependencyFactoryImpl = new DependencyFactory(reflectionutils);
        this.dependencyContextService = new DependencyContextService(reflectionTool, dependencyContext, dependencyFactoryImpl);
    }

    @Test
    public void isLeafParameter_onlyAutowiredConstructor_shouldReturnTrue() throws Exception {
        this.dependency = createDependency(DummyTestClass.class);
        assertTrue(dependency.isLeafParameter());
    }

    private Dependency createDependency(Class<?> argumentClass) throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        return new DependencyFactory(reflectionutils).createDependencyComponent(argumentClass, dependencyContextService);
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
        Dependency dependency = new DependencyFactory(reflectionutils).createDependencyComponent(ClassWithInjectionField.class, dependencyContextService);
        final Object dependentParameters = new ReflectionTestHelper().getField(dependency, "dependentParameters");
        assertTrue(((DependentParams) dependentParameters).getFieldInjectedInstances().length == 1);
    }

}