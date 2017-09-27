package model;

import org.junit.Test;
import tooling.ClassWithInjectionField;
import tooling.DummyTestClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DependencyTest {

    private DependencyContextService dependencyContextService;
    private Dependency dependency;
    private ReflectionRepresentation representation;

    @Test
    public void isLeafParameter_onlyAutowiredConstructor_shouldReturnTrue() throws Exception {
        dependencyContextService = mock(DependencyContextService.class);
        returnRepresentation();
        Constructor<?>[] declaredConstructors = DummyTestClass.class.getDeclaredConstructors();
        when(representation.getNoArgsConstructor()).thenReturn(Optional.of(declaredConstructors[0]));
        this.dependency = new Dependency(DummyTestClass.class, dependencyContextService, representation);
        assertTrue(dependency.isLeafParameter());
    }


    private ReflectionTool returnRepresentation() {
        ReflectionTool reflectionToolMock = mock(ReflectionTool.class);
        representation = mock(ReflectionRepresentation.class);
        when(reflectionToolMock.getReflectionRepresentation(any())).thenReturn(representation);
        return reflectionToolMock;
    }

    @Test
    public void isLeafParameter_onlyFieldInjections_shouldReturnFalse() throws Exception {
        dependencyContextService = mock(DependencyContextService.class);
        returnRepresentation();
        when(representation.getNoArgsConstructor()).thenReturn(Optional.empty());
        this.dependency = new Dependency(ClassWithInjectionField.class, dependencyContextService, representation);
        assertNotALeafParameter();
    }

    private void assertNotALeafParameter() {
        assertTrue(!dependency.isLeafParameter());
    }

    @Test
    public void instantiateDependentParameters_onlyFieldInjections_shouldReturnTrue() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        dependencyContextService = mock(DependencyContextService.class);
        returnRepresentation();
        when(representation.getParamTypesFromArgsConstructor()).thenReturn(new Class[]{DummyTestClass.class});
        Dependency dependencyMock = mock(Dependency.class);
        when(dependencyContextService.instantiateListOfDependencies(any())).thenReturn(Collections.singletonList(dependencyMock));
        Dependency dependency = new Dependency(ClassWithInjectionField.class, dependencyContextService, representation);
        assertTrue(dependency.instantiateDependentParameters());
    }

}