package model;

import org.junit.Test;
import tooling.ClassWithInjectionField;
import tooling.DependencyContextService;
import tooling.DummyTestClass;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DependencyTest {

    private DependencyContextService dependencyContextService;
    private Dependency dependency;

    @Test
    public void isLeafParameter_onlyAutowiredConstructor_shouldReturnTrue() throws Exception {
        dependencyContextService = mock(DependencyContextService.class);
        this.dependency = new Dependency(DummyTestClass.class, dependencyContextService);
        assertTrue(dependency.isLeafParameter());
    }

    @Test
    public void isLeafParameter_onlyFieldInjections_shouldReturnFalse() throws Exception {
        dependencyContextService = mock(DependencyContextService.class);
        this.dependency = new Dependency(ClassWithInjectionField.class, dependencyContextService);
        assertNotALeafParameter();
    }

    private void assertNotALeafParameter() {
        assertTrue(!dependency.isLeafParameter());
    }

    @Test
    public void instantiateDependentParameters_onlyFieldInjections_shouldReturnTrue() {
        dependencyContextService = mock(DependencyContextService.class);
        Dependency dependency = new Dependency(ClassWithInjectionField.class, dependencyContextService);
        dependency.instantiateDependentParameters();
    }

}