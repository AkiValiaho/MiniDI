package model;

import org.junit.Test;
import tooling.*;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 29.9.2017.
 */
public class MatchedPairTest {
    @Test
    public void matchPairs() throws Exception {
        ClassWithFieldAndConstructor classWithFieldAndConstructor = new ClassWithFieldAndConstructor(new DummyTestClassWithDependency(new DummyTestClass()));
        List<MatchedPair> matchedPairs = new MatchedPair(classWithFieldAndConstructor.getClass().getDeclaredFields(), new Object[]{new DummyTestClass(), new ClassWithInjectionField()}).matchPairs();
        assertEquals(matchedPairs.size(), 2);
        assertTrue(new ClassNameMatcher().classNamesMatch(matchedPairs.get(0).getMatchedField(), matchedPairs.get(0).getMatchedInstance()));
    }

    @Test
    public void setFieldToInstance() throws Exception {
        ClassWithFieldAndConstructor classWithFieldAndConstructor = new ClassWithFieldAndConstructor(new DummyTestClassWithDependency(new DummyTestClass()));
        MatchedPair matchedPairs = new MatchedPair(classWithFieldAndConstructor.getClass().getDeclaredFields(), new Object[]{new DummyTestClass(), new ClassWithInjectionField()});
        List<MatchedPair> matchedPairs1 = matchedPairs.matchPairs();
        assertFieldsAreSet(classWithFieldAndConstructor, matchedPairs1);
    }

    private void assertFieldsAreSet(ClassWithFieldAndConstructor classWithFieldAndConstructor, List<MatchedPair> matchedPairs1) throws IllegalAccessException, NoSuchFieldException {
        matchedPairs1.get(0).setFieldToInstance(classWithFieldAndConstructor);
        matchedPairs1.get(1).setFieldToInstance(classWithFieldAndConstructor);
        assertTrue(new ReflectionTestHelper<>().getField(classWithFieldAndConstructor, "dummyTestClass") != null);
        assertTrue(new ReflectionTestHelper<>().getField(classWithFieldAndConstructor, "classWithInjectionField") != null);
    }

}