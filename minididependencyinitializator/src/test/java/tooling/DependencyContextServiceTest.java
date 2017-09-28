package tooling;

import model.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

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
    //TODO Finish this feature
    @Ignore
    public void createDependencyFromResource_classHasBothFieldAndConstructorInjections_shouldCreateDependency() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        final Dependency dependenciesFromResource = dependencyContextService.createDependenciesFromResource(ClassWithFieldAndConstructor.class);
        final Map dependentParameters = (Map) new ReflectionTestHelper().getField(dependenciesFromResource, "dependentParameters");
        assertEquals(2, dependentParameters.size());
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


    @Test(expected = NullPointerException.class)
    public void createDependency_invalidNullClass_shouldThrowClassNotFoundException() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Dependency dependency = dependencyContextService.createDependenciesFromResource(null);
    }
}