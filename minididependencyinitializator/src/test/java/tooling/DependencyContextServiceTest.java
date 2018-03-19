package tooling;

import model.*;
import model.dummyClasses.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import tooling.tree.CycleChecker;
import tooling.tree.CycleCheckingDependencyFactory;
import tooling.tree.PriorityCheckingDependencyFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 7.9.2017.
 */
public class DependencyContextServiceTest {
    @Rule
    public ExpectedSystemExit exit = ExpectedSystemExit.none();
    private DependencyContextService dependencyContextService;
    private DependencyContext dependencyContext;
    private ReflectionToolSet reflectionToolSet;

    @Before
    public void before() {
        final ReflectionTool reflectionTool = new ReflectionTool();
        this.reflectionToolSet = new ReflectionUtils();
        dependencyContext = new DependencyContext();
        final DependencyComponentFactory dependencyComponentFactory = new PriorityCheckingDependencyFactory(new CycleCheckingDependencyFactory(new DependencyFactory(reflectionToolSet), new CycleChecker(reflectionToolSet), reflectionToolSet), reflectionToolSet);
        this.dependencyContextService = new DependencyContextService(reflectionTool, dependencyContext, dependencyComponentFactory, reflectionToolSet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDependencyFromResource_hasPostConstructInNonVoidMethod_shouldThrowException() throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        final ReflectionTestHelper reflectionTestHelper = new ReflectionTestHelper();
        final Dependency dependenciesFromResource = dependencyContextService.createDependenciesFromResource(ClassWithPostConstructWrongParameters.class);
    }

    @Test
    public void createDependencyFromResource_hasPostConstructAnnotation_shouldCreateDependencyAndCallPostConstruct() throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        final ReflectionTestHelper reflectionTestHelper = new ReflectionTestHelper();
        final Dependency dependenciesFromResource = dependencyContextService.createDependenciesFromResource(ClassWithPostConstruct.class);
        final ClassWithPostConstruct dependencyInstance = (ClassWithPostConstruct) dependenciesFromResource.getDependencyInstance();
        try {
            final String something = (String) reflectionTestHelper.getField(dependencyInstance, "something");
            checkNotNull(something);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void instantiateListOfDependencies_classThatCausesInjectionError_shouldCallSystemExit() {
        exit.expectSystemExit();
        final Class[] classes = {CyclicClassA.class};
        dependencyContextService.instantiateListOfDependencies(classes);
    }

    @Test
    public void createDependencyFromResource_hasADependsOnAnnotation_priorityDependencyInstantiatedFirst() throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        dependencyContextService.createDependenciesFromResource(ClassWithPriorityDependency.class);
        assertTrue(dependencyContext.comesFirst(PriorityDependency.class, ClassWithPriorityDependency.class));

    }

    @Test(expected = CyclicDependencyException.class)
    public void createDependencyFromResource_cyclicalDependency_shouldThrowException() throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        dependencyContextService.createDependenciesFromResource(CyclicClassA.class);
    }

    @Test(expected = CyclicDependencyException.class)
    public void createDependencyFromResource_transitiveCyclicaldependency_shouldThrowException() throws InvocationTargetException, CyclicDependencyException, InstantiationException, IllegalAccessException {
        dependencyContextService.createDependenciesFromResource(CyclicClassTransitiveA.class);
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
        assertTrue(new ClassNameMatcher().allClassNamesMatch(objects, classes));
        assertTrue(new ClassNameMatcher().allClassNamesMatch(Arrays.asList(dependenciesFromResource.getConstructorDependentInstances()),
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