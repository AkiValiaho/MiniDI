package tooling;

import model.ClassPathResource;
import model.Dependency;
import model.DependencyContext;
import model.ReflectionTool;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Aki on 7.9.2017.
 */
public class ReflectionToolTest {
    private ReflectionTool reflectionInitializertool;
    private DependencyContextService dependencyContextService;

    @Before
    public void setUp() throws Exception {
        final DependencyContext mock = mock(DependencyContext.class);
        this.dependencyContextService = mock(DependencyContextService.class);
        this.reflectionInitializertool = new ReflectionTool();
    }

    @Test
    public void findClassPathResources_nonLeafClass_shouldFindAllClassesUnderPackage() throws Exception {
        final List<ClassPathResource> classPathResources = reflectionInitializertool.findClassPathResources(DummyTestClassWithDependency.class);
    }

    @Test
    public void initialize_classWithFieldInjection_shouldInitiateClassWithfieldInjection() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Dependency dependency = new Dependency(ClassWithInjectionField.class, dependencyContextService, reflectionInitializertool);
        reflectionInitializertool.initialize(dependency);
    }

    @Test
    public void initialize_recursiveNonLeafClass_shouldInitiateRecursiveNonLeafClass() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Dependency dependency = new Dependency(RecursiveDummyTestClass.class, dependencyContextService, reflectionInitializertool);
        DummyTestClassWithDependency dummyTestClassWithDependency = new DummyTestClassWithDependency(new DummyTestClass());
        when(dependencyContextService.instantiateListOfDependencies(any())).thenReturn(Collections.singletonList(new Dependency(DummyTestClassWithDependency.class, dependencyContextService, reflectionInitializertool)));
        final Object initialize = reflectionInitializertool.initialize(dependency);
        assertNotNull(initialize);
    }

    @Test
    public void initialize_nonLeafClass_shouldInitializeNonLeafClass() throws Exception {
        final Dependency dependency = new Dependency(DummyTestClassWithDependency.class, dependencyContextService, reflectionInitializertool);
        final DummyTestClass dummyTestClass = new DummyTestClass();
        final Dependency dummyDependency = new Dependency(DummyTestClass.class, dependencyContextService, reflectionInitializertool);
        dummyDependency.setDependencyInstance(dummyTestClass);
        when(dependencyContextService.instantiateListOfDependencies(any())).thenReturn(Collections.singletonList(dummyDependency));
        final Object initialize = reflectionInitializertool.initialize(dependency);
        assertEquals(dummyTestClass, ((DummyTestClassWithDependency) initialize).getDependency());
    }

    @Test
    public void initialize_dummyLeafClass_shouldInitializeDummyTestClass() throws Exception {
        final Dependency dependency = new Dependency(DummyTestClass.class, dependencyContextService, reflectionInitializertool);
        final Object initialize = reflectionInitializertool.initialize(dependency);
        assertTrue(initialize != null);
    }

    @Test(expected = MultipleAnnotatedConstructorsException.class)
    public void initialize_nonLeafClassMultipleAutowiredConstructors_shouldThrowAnException() throws MultipleAnnotatedConstructorsException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Dependency dependency = new Dependency(DummyTestClassMultipleAnnotations.class, dependencyContextService, reflectionInitializertool);
        reflectionInitializertool.initialize(dependency);
    }

    @Test
    public void initialize_nonLeafClass_shouldFindConstructorWithAutowiredAnnotation() throws Exception {
        final Dependency dependency = new Dependency(DummyTestClassWithDependency.class, dependencyContextService, reflectionInitializertool);
        final DummyTestClass dummyInstance = new DummyTestClass();
        final Dependency dummyDependency = new Dependency(DummyTestClass.class, dependencyContextService, reflectionInitializertool);
        dummyDependency.setDependencyInstance(dummyInstance);
        List<Dependency> mockListOfObjects = Collections.singletonList(dummyDependency);
        when(dependencyContextService.instantiateListOfDependencies(any())).thenReturn(mockListOfObjects);
        final Object initialize = reflectionInitializertool.initialize(dependency);
        assertClassWithDependencyIsCreated(dummyInstance, initialize);
    }

    private void assertClassWithDependencyIsCreated(DummyTestClass dummyInstance, Object initialize) {
        assertTrue(initialize != null);
        assertEquals(((DummyTestClassWithDependency) initialize).getDependency(), dummyInstance);
    }

}