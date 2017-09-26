package tooling;

import lombok.Getter;
import model.*;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        final DependencyFactory dependencyFactory = dependencyContextAndReflectionTool.getDependencyFactory();
        final Dependency dependency = mock(Dependency.class);
        when(dependencyFactory.createDependency(any(), any(), any())).thenReturn(dependency);
        this.dependencyContextService = new DependencyContextService(reflectionInitializerMock, dependencyContextMock, dependencyFactory);
        final List<Dependency> dependencies = dependencyContextService.instantiateListOfDependencies(new Class[]{DummyTestClass.class, DummyTestClassWithDependency.class});
        assertTrue(dependencies.size() == 2);
    }

    @Test
    public void createDependency_validClass_shouldCreateDependencyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        DependencyContextAndReflectionTool dependencyContextAndReflectionTool = new DependencyContextAndReflectionTool().invoke();
        ReflectionTool reflectionInitializerMock = dependencyContextAndReflectionTool.getReflectionInitializerMock();
        final DependencyContext dependencyContextMock = dependencyContextAndReflectionTool.getDependencyContextMock();
        final DependencyFactory dependencyFactory = dependencyContextAndReflectionTool.getDependencyFactory();
        final DependencyContextService dependencyContextService = new DependencyContextService(reflectionInitializerMock, dependencyContextMock, dependencyFactory);
        final Dependency dependency = mock(Dependency.class);
        when(dependencyFactory.createDependency(any(), any(), any())).thenReturn(dependency);
        final Dependency dependenciesFromResource = dependencyContextService.createDependenciesFromResource(DummyTestClass.class);
        assertEquals(dependenciesFromResource, dependency);
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
        final DependencyContextService dependencyContextService = new DependencyContextService(reflectionInitializerMock, dependencyContextMock, mock(DependencyFactory.class));
        final Dependency dependency = dependencyContextService.createDependenciesFromResource(null);

    }


    private class DependencyContextAndReflectionTool {
        private DependencyContext dependencyContextMock;
        private ReflectionTool reflectionInitializerMock;
        @Getter
        private DependencyFactory dependencyFactory;

        public DependencyContext getDependencyContextMock() {
            return dependencyContextMock;
        }

        public ReflectionTool getReflectionInitializerMock() {
            return reflectionInitializerMock;
        }

        public DependencyContextAndReflectionTool invoke() {
            dependencyContextMock = mock(DependencyContext.class);
            reflectionInitializerMock = mock(ReflectionTool.class);
            dependencyFactory = mock(DependencyFactory.class);
            return this;
        }
    }
}