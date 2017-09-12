package tooling;

import annotations.Autowired;

/**
 * Created by Aki on 8.9.2017.
 */
public class DummyTestClassMultipleAnnotations {
    @Autowired
    public DummyTestClassMultipleAnnotations(DummyTestClass dummyTestClass) {

    }

    @Autowired
    public DummyTestClassMultipleAnnotations(DummyTestClass dummyTestClass, DependencyContextService dependencyContextService) {

    }
}
