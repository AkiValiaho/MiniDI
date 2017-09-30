package model;

import org.junit.Test;
import tooling.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 29.9.2017.
 */
public class MatchedPairTest {

    @Test
    public void setFieldsToInstance() throws Exception {
        ClassWithFieldAndConstructor classWithFieldAndConstructor = new ClassWithFieldAndConstructor(new DummyTestClassWithDependency(new DummyTestClass()));
        MatchedPair matchedPairs = new MatchedPair(classWithFieldAndConstructor.getClass().getDeclaredFields(), new Object[]{new DummyTestClass(), new ClassWithInjectionField()});
        matchedPairs.setFieldsToInstance(classWithFieldAndConstructor);
        assertFieldsAreSet(classWithFieldAndConstructor);
    }

    private void assertFieldsAreSet(ClassWithFieldAndConstructor classWithFieldAndConstructor) throws IllegalAccessException, NoSuchFieldException {
        assertTrue(new ReflectionTestHelper<>().getField(classWithFieldAndConstructor, "dummyTestClass") != null);
        assertTrue(new ReflectionTestHelper<>().getField(classWithFieldAndConstructor, "classWithInjectionField") != null);
    }

}