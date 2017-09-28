package model;

import org.junit.Before;
import org.junit.Test;
import tooling.DummyTestClassWithDependency;
import tooling.DummyTestClassWithFieldDependency;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReflectionRepresentationTest {
    private ReflectionRepresentation reflectionRepresentation;
    private ReflectionTool reflectionTool;

    @Before
    public void setUp() throws Exception {
        reflectionTool = new ReflectionTool();
        this.reflectionRepresentation = new ReflectionRepresentation(DummyTestClassWithDependency.class, reflectionTool);
    }

    @Test
    public void hasNoInjectedFields() throws Exception {
        assertTrue(!reflectionRepresentation.hasInjectedFields());
    }

    @Test
    public void hasInjectedFields() throws Exception {
        this.reflectionRepresentation = new ReflectionRepresentation(DummyTestClassWithFieldDependency.class, reflectionTool);
        assertTrue(reflectionRepresentation.hasInjectedFields());
    }

    @Test
    public void getNoArgsConstructor() throws Exception {
        assertNotNull(reflectionRepresentation.getNoArgsConstructor());
    }

    @Test
    public void getDependentParamsFromFields() throws Exception {
        //reflectionRepresentation.getDependentParamsFromFields();
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