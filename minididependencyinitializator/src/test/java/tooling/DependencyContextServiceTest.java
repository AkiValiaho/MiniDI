package tooling;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void createDependencyFromResource_classHasBothFieldAndConstructorInjections_shouldCreateDependency() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, CyclicDependencyException {
        final Dependency dependenciesFromResource = dependencyContextService.createDependenciesFromResource(ClassWithFieldAndConstructor.class);
        assertEquals(dependenciesFromResource.getFieldDependentInstances().length, 2);
        verifyFieldAndConstructorInjectionsMatch(dependenciesFromResource);
    }

    private void verifyFieldAndConstructorInjectionsMatch(Dependency dependenciesFromResource) {
        final List<Object> objects = Arrays.asList(dependenciesFromResource.getFieldDependentInstances());
        final List<Class> classes = Arrays.asList(new Class[]{DummyTestClass.class, ClassWithInjectionField.class});
        assertTrue(new ClassNameMatcher().classNamesMatch(objects, classes));
        assertTrue(new ClassNameMatcher().classNamesMatch(Arrays.asList(dependenciesFromResource.getConstructorDependentInstances()),
                Arrays.asList(new Class[]{DummyTestClassWithDependency.class})));
    }

    @Test
    public void createListOfDependencies_validClasses_shouldCreateListOfDependencies() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final List<Dependency> dependencies = dependencyContextService.instantiateListOfDependencies(new Class[]{DummyTestClass.class, DummyTestClassWithDependency.class});
        assertTrue(dependencies.size() == 2);
    }

    @Test
    public void createDependency_validClass_shouldCreateDependencyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        final Dependency dependenciesFromResource = dependencyContextService.createDependenciesFromResource(DummyTestClass.class);
        assertTrue(dependenciesFromResource.getDependencyInstance() instanceof DummyTestClass);
    }


    @Test(expected = NullPointerException.class)
    public void createDependency_invalidNullClass_shouldThrowClassNotFoundException() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        final Dependency dependency = dependencyContextService.createDependenciesFromResource(null);
    }
}