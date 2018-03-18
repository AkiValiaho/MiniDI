package model;

import model.dummyClasses.DummyTestClassB;
import org.junit.Test;
import tooling.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 29.9.2017.
 */
public class MatchedPairInjectorTest {

    @Test
    public void setFieldsToInstance_privateField_shouldThrowIllegalAccessException() throws IllegalAccessException {
        ClassWithFieldAndConstructor classWithFieldAndConstructor = new ClassWithFieldAndConstructor(new DummyTestClassWithDependency(new DummyTestClassB()));
        MatchedPairInjector matchedPairsInjector = new MatchedPairInjector(classWithFieldAndConstructor.getClass().getDeclaredFields(), new Object[]{new DummyTestClass(), new ClassWithInjectionField()}, classWithFieldAndConstructor);
    }

    @Test
    public void setFieldsToInstance() throws Exception {
        ClassWithFieldAndConstructor classWithFieldAndConstructor = new ClassWithFieldAndConstructor(new DummyTestClassWithDependency(new DummyTestClassB()));
        MatchedPairInjector matchedPairsInjector = new MatchedPairInjector(classWithFieldAndConstructor.getClass().getDeclaredFields(), new Object[]{new DummyTestClass(), new ClassWithInjectionField()}, classWithFieldAndConstructor);
        assertFieldsAreSet(classWithFieldAndConstructor);
    }

    private void assertFieldsAreSet(ClassWithFieldAndConstructor classWithFieldAndConstructor) throws IllegalAccessException, NoSuchFieldException {
        assertTrue(new ReflectionTestHelper<>().getField(classWithFieldAndConstructor, "dummyTestClass") != null);
        assertTrue(new ReflectionTestHelper<>().getField(classWithFieldAndConstructor, "classWithInjectionField") != null);
    }

}