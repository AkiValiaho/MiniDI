package tooling;

import model.dummyClasses.DummyTestClassB;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 9.10.2017.
 */
public class ClassNameMatcherTest {
    private ClassNameMatcher classNameMatcher;

    @Before
    public void setUp() throws Exception {
        this.classNameMatcher = new ClassNameMatcher();
    }


    @Test
    public void classNamesMatch_namesDontMatch_shouldReturnFalse() throws Exception {
        final List<Object> dummyTestClasses = Collections.singletonList(new DummyTestClass());
        assertTrue(doesNotMatch(dummyTestClasses, Collections.singletonList(DummyTestClassB.class)));
    }

    private boolean doesNotMatch(List<Object> dummyTestClasses, List<Class> classes) {
        return !this.classNameMatcher.allClassNamesMatch(dummyTestClasses, classes);
    }

    @Test
    public void classNamesMatch_namesMatch_shouldReturnTrue() throws Exception {
        final List<Object> dummyTestClasses = Collections.singletonList(new DummyTestClass());
        assertTrue(this.classNameMatcher.allClassNamesMatch(dummyTestClasses, Collections.singletonList(DummyTestClass.class)));
    }

}