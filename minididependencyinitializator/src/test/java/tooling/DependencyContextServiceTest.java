package tooling;

import model.Dependency;
import model.DependencyContext;
import model.ReflectionTool;
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
    public void createListOfDependencies_validClasses_shouldCreateListOfDependencies() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final DependencyContextAndReflectionTool dependencyContextAndReflectionTool = new DependencyContextAndReflectionTool().invoke();
        final ReflectionTool reflectionInitializerMock = dependencyContextAndReflectionTool.getReflectionInitializerMock();
        final DependencyContext dependencyContextMock = dependencyContextAndReflectionTool.getDependencyContextMock();
        this.dependencyContextService = new DependencyContextService(reflectionInitializerMock, dependencyContextAndReflectionTool.getDependencyContextMock());
        dependencyContextService.instantiateListOfDependencies(new Class[]{DummyTestClass.class, DummyTestClassWithDependency.class});
        verify(reflectionInitializerMock, times(2)).initialize(any());
        verify(dependencyContextMock, times(2)).addDependencyToMap(any());
    }

    @Test
    public void createDependency_validClass_shouldCreateDependencyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        DependencyContextAndReflectionTool dependencyContextAndReflectionTool = new DependencyContextAndReflectionTool().invoke();
        ReflectionTool reflectionInitializerMock = dependencyContextAndReflectionTool.getReflectionInitializerMock();
        DependencyContext dependencyContextMock = dependencyContextAndReflectionTool.getDependencyContextMock();
        final DummyTestClass initializedDummyTestClass = new DummyTestClass();
        final Dependency dependency1 = new Dependency(DummyTestClass.class, dependencyContextService, reflectionInitializerMock);
        when(reflectionInitializerMock.initialize(any())).thenReturn(initializedDummyTestClass);
        dependencyContextService = new DependencyContextService(reflectionInitializerMock, dependencyContextMock);
        final Dependency dependency = dependencyContextService.createDependenciesFromResource(DummyTestClass.class);
        assertMocksCalled(dependencyContextMock, initializedDummyTestClass, dependency);
    }

    private void assertMocksCalled(DependencyContext dependencyContextMock, DummyTestClass initializedDummyTestClass, Dependency dependency) {
        verify(dependencyContextMock, times(1)).addDependencyToMap(any());
        assertEquals(dependency.getDependencyInstance().getClass(), initializedDummyTestClass.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void createDependency_invalidNullClass_shouldThrowClassNotFoundException() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final DependencyContextAndReflectionTool dependencyContextAndReflectionTool = new DependencyContextAndReflectionTool();
        final DependencyContext dependencyContextMock = dependencyContextAndReflectionTool.getDependencyContextMock();
        final ReflectionTool reflectionInitializerMock = dependencyContextAndReflectionTool.getReflectionInitializerMock();
        final DependencyContextService dependencyContextService = new DependencyContextService(reflectionInitializerMock, dependencyContextMock);
        final Dependency dependency = dependencyContextService.createDependenciesFromResource(null);

    }


    private class DependencyContextAndReflectionTool {
        private DependencyContext dependencyContextMock;
        private ReflectionTool reflectionInitializerMock;

        public DependencyContext getDependencyContextMock() {
            return dependencyContextMock;
        }

        public ReflectionTool getReflectionInitializerMock() {
            return reflectionInitializerMock;
        }

        public DependencyContextAndReflectionTool invoke() {
            dependencyContextMock = mock(DependencyContext.class);
            reflectionInitializerMock = mock(ReflectionTool.class);
            return this;
        }
    }
}