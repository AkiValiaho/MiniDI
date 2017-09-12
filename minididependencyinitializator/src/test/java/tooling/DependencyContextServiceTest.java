package tooling;

import model.Dependency;
import model.DependencyContext;
import model.ReflectionInitializerTool;
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
        DependencyContextAndReflectionTool dependencyContextAndReflectionTool = new DependencyContextAndReflectionTool().invoke();
        ReflectionInitializerTool reflectionInitializerMock = dependencyContextAndReflectionTool.getReflectionInitializerMock();
        DependencyContext dependencyContextMock = dependencyContextAndReflectionTool.getDependencyContextMock();
        final DummyTestClass initializedDummyTestClass = new DummyTestClass();
        final Dependency dependency1 = new Dependency(DummyTestClass.class, dependencyContextService);
        when(reflectionInitializerMock.initialize(any())).thenReturn(initializedDummyTestClass);
        dependencyContextService = new DependencyContextService(reflectionInitializerMock,dependencyContextMock);
        final Dependency dependency = dependencyContextService.createDependency(DummyTestClass.class);
        assertMocksCalled(dependencyContextMock, initializedDummyTestClass, dependency);
    }

    private void assertMocksCalled(DependencyContext dependencyContextMock, DummyTestClass initializedDummyTestClass, Dependency dependency) {
        verify(dependencyContextMock, times(1)).addDependencyToMap(any());
        assertEquals(dependency.getDependencyInstance().getClass(),initializedDummyTestClass.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void createDependency_invalidNullClass_shouldThrowClassNotFoundException() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final DependencyContextAndReflectionTool dependencyContextAndReflectionTool = new DependencyContextAndReflectionTool();
        final DependencyContext dependencyContextMock = dependencyContextAndReflectionTool.getDependencyContextMock();
        final ReflectionInitializerTool reflectionInitializerMock = dependencyContextAndReflectionTool.getReflectionInitializerMock();
        final DependencyContextService dependencyContextService = new DependencyContextService(reflectionInitializerMock, dependencyContextMock);
        final Dependency dependency = dependencyContextService.createDependency(null);

    }


    private class DependencyContextAndReflectionTool {
        private DependencyContext dependencyContextMock;
        private ReflectionInitializerTool reflectionInitializerMock;

        public DependencyContext getDependencyContextMock() {
            return dependencyContextMock;
        }

        public ReflectionInitializerTool getReflectionInitializerMock() {
            return reflectionInitializerMock;
        }

        public DependencyContextAndReflectionTool invoke() {
            dependencyContextMock = mock(DependencyContext.class);
            reflectionInitializerMock = mock(ReflectionInitializerTool.class);
            return this;
        }
    }
}