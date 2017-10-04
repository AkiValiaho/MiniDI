package model;

import org.junit.Before;
import org.junit.Test;
import tooling.DummyTestClassWithDependency;
import tooling.DummyTestClassWithFieldDependency;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DependencyReflectionRepresentationTest {
    private DependencyReflectionRepresentation dependencyReflectionRepresentation;
    private ReflectionTool reflectionTool;

    @Before
    public void setUp() throws Exception {
        reflectionTool = new ReflectionTool();
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(DummyTestClassWithDependency.class, reflectionTool);
    }

    @Test
    public void hasNoInjectedFields() throws Exception {
        assertTrue(!dependencyReflectionRepresentation.hasInjectedFields());
    }

    @Test
    public void hasInjectedFields() throws Exception {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(DummyTestClassWithFieldDependency.class, reflectionTool);
        assertTrue(dependencyReflectionRepresentation.hasInjectedFields());
    }

    @Test
    public void getNoArgsConstructor() throws Exception {
        assertNotNull(dependencyReflectionRepresentation.getNoArgsConstructor());
    }

    @Test
    public void getDependentParamsFromFields() throws Exception {
        //dependencyReflectionRepresentation.getDependentParamsFromFields();
    }

    @Test
    public void represent() throws Exception {
    }

    @Test
    public void getParamTypesFromArgsConstructor() throws Exception {
    }

    @Test
    public void getArgsConstructor() throws Exception {
    }

}