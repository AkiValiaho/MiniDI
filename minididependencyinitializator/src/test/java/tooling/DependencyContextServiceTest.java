package tooling;

import model.Dependency;
import model.DependencyContext;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Aki on 7.9.2017.
 */
public class DependencyContextServiceTest {
    private DependencyContextService dependencyContextService;

    @Test
    public void createDependency_validClass_shouldCreateDependencyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final DependencyContext dependencyContextMock = mock(DependencyContext.class);
        final ReflectionInitializerTool reflectionInitializerMock = mock(ReflectionInitializerTool.class);
        final DummyTestClass initializedDummyTestClass = new DummyTestClass();
        when(reflectionInitializerMock.initialize(DummyTestClass.class)).thenReturn(initializedDummyTestClass);
        dependencyContextService = new DependencyContextService(reflectionInitializerMock,dependencyContextMock);
        final Dependency dependency = dependencyContextService.createDependency(DummyTestClass.class);
        assertMocksCalled(dependencyContextMock, initializedDummyTestClass, dependency);
    }

    private void assertMocksCalled(DependencyContext dependencyContextMock, DummyTestClass initializedDummyTestClass, Dependency dependency) {
        verify(dependencyContextMock, times(1)).addDependencyToMap(dependency);
        assertEquals(dependency.getDependencyInstance(), initializedDummyTestClass);
    }

    @Test(expected = ClassNotFoundException.class)
    @Ignore
    public void createDependency_invalidClass_shouldThrowClassNotFoundException() throws ClassNotFoundException {
        //TODO
    }
}