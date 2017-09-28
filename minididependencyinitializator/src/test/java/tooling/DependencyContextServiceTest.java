package tooling;

import lombok.Getter;
import model.*;
import org.junit.Before;
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

    @Before
    public void before() {
        final ReflectionTool reflectionTool = new ReflectionTool();
        final DependencyContext dependencyContext = new DependencyContext();
        final DependencyFactory dependencyFactory = new DependencyFactory();
        this.dependencyContextService = new DependencyContextService(reflectionTool, dependencyContext, dependencyFactory);
    }

    @Test
    public void createListOfDependencies_validClasses_shouldCreateListOfDependencies() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final List<Dependency> dependencies = dependencyContextService.instantiateListOfDependencies(new Class[]{DummyTestClass.class, DummyTestClassWithDependency.class});
        assertTrue(dependencies.size() == 2);
    }

    @Test
    public void createDependency_validClass_shouldCreateDependencyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Dependency dependenciesFromResource = dependencyContextService.createDependenciesFromResource(DummyTestClass.class);
        assertTrue(dependenciesFromResource.getDependencyInstance() instanceof DummyTestClass);
    }


    private void assertMocksCalled(DependencyContext dependencyContextMock, DummyTestClass initializedDummyTestClass, Dependency dependency) {
        verify(dependencyContextMock, times(1)).addDependencyToMap(any());
        assertEquals(dependency.getDependencyInstance().getClass(), initializedDummyTestClass.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void createDependency_invalidNullClass_shouldThrowClassNotFoundException() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
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