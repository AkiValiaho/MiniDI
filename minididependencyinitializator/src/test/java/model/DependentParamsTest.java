package model;

import model.dummyClasses.DummyTestClassB;
import org.junit.Before;
import org.junit.Test;
import tooling.ClassWithFieldAndConstructor;
import tooling.DependencyContextService;
import tooling.DummyTestClassWithDependency;
import tooling.ReflectionTool;

import static org.junit.Assert.assertTrue;

public class DependentParamsTest {

    private DependentParams dependentParams;
    private DependencyContextService dependencyContextService;
    private ReflectionTool reflectionTool;
    private ReflectionToolSet reflectionutils;

    @Before
    public void setUp() throws Exception {
        dependentParams = new DependentParams();
        reflectionTool = new ReflectionTool();
        this.reflectionutils = new ReflectionUtils();
        this.dependencyContextService = new DependencyContextService(reflectionTool,
                new DependencyContext(),
                new DependencyFactory(reflectionutils), reflectionutils);
    }

    @Test
    public void getDependentParamsForClass() throws Exception {
        ClassWithFieldAndConstructor classWithFieldAndConstructor = new ClassWithFieldAndConstructor(new DummyTestClassWithDependency(new DummyTestClassB()));
        DependentParams dependentParamsForClass = dependentParams.getDependentParamsForClass(new DependencyReflectionRepresentation(classWithFieldAndConstructor.getClass(), reflectionutils), dependencyContextService);
        assertTrue(dependentParamsForClass.getConstructorInjectedInstances().length == 1);
        assertTrue(dependentParamsForClass.getFieldInjectedInstances().length == 2);
    }
}