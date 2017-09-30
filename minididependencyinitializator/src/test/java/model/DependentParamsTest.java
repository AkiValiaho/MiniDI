package model;

import org.junit.Before;
import org.junit.Test;
import tooling.ClassWithFieldAndConstructor;
import tooling.DummyTestClass;
import tooling.DummyTestClassWithDependency;

import static org.junit.Assert.assertTrue;

public class DependentParamsTest {

    private DependentParams dependentParams;
    private DependencyContextService dependencyContextService;
    private ReflectionTool reflectionTool;

    @Before
    public void setUp() throws Exception {
        dependentParams = new DependentParams();
        reflectionTool = new ReflectionTool();
        this.dependencyContextService = new DependencyContextService(reflectionTool,
                new DependencyContext(),
                new DependencyFactory());
    }

    @Test
    public void getDependentParamsForClass() throws Exception {
        ClassWithFieldAndConstructor classWithFieldAndConstructor = new ClassWithFieldAndConstructor(new DummyTestClassWithDependency(new DummyTestClass()));
        DependentParams dependentParamsForClass = dependentParams.getDependentParamsForClass(new ReflectionRepresentation(classWithFieldAndConstructor.getClass(), reflectionTool), dependencyContextService);
        assertTrue(dependentParamsForClass.getConstructorInjectedInstances().length == 1);
        assertTrue(dependentParamsForClass.getFieldInjectedInstances().length == 2);
    }
}