package model;

import org.junit.Before;
import org.junit.Test;
import tooling.DummyTestClassWithDependency;
import tooling.DummyTestClassWithFieldDependency;

import java.lang.reflect.Constructor;
import java.util.Optional;

import static org.junit.Assert.*;

public class DependencyReflectionRepresentationTest {
    private DependencyReflectionRepresentation dependencyReflectionRepresentation;
    private ReflectionUtils reflectionutils;

    @Before
    public void setUp() throws Exception {
        this.reflectionutils = new ReflectionUtils();
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(DummyTestClassWithDependency.class, reflectionutils);
    }

    @Test
    public void hasNoInjectedFields() throws Exception {
        assertTrue(!dependencyReflectionRepresentation.hasInjectedFields());
    }

    @Test
    public void hasInjectedFields() throws Exception {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(DummyTestClassWithFieldDependency.class, reflectionutils);
        assertTrue(dependencyReflectionRepresentation.hasInjectedFields());
    }

    @Test
    public void getNoArgsConstructor() throws Exception {
        assertNotNull(dependencyReflectionRepresentation.getNoArgsConstructor());
    }

    @Test
    public void getDependentParamsFromFields() throws Exception {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(DummyTestClassWithFieldDependency.class, reflectionutils);
        assertEquals(dependencyReflectionRepresentation.getDependentParamsFromFields().length, 1);
    }

    @Test
    public void represent() throws Exception {
        this.dependencyReflectionRepresentation = new DependencyReflectionRepresentation(DummyTestClassWithFieldDependency.class, reflectionutils);
        DependencyReflectionRepresentation represent = dependencyReflectionRepresentation.represent();
        Class<?>[] dependentParamsFromFields = represent.getDependentParamsFromFields();
        assertEquals(dependentParamsFromFields.length, 1);
    }

    @Test
    public void getParamTypesFromArgsConstructor() throws Exception {
        Class<?>[] paramTypesFromArgsConstructor = dependencyReflectionRepresentation.getParamTypesFromArgsConstructor();
        assertEquals(paramTypesFromArgsConstructor.length, 1);
    }

    @Test
    public void getArgsConstructor() throws Exception {
        Optional<Constructor> argsConstructor = dependencyReflectionRepresentation.getArgsConstructor();
        assertTrue(argsConstructor.isPresent());
    }

}