package tooling;

import annotations.Autowired;
import annotations.Component;

/**
 * Created by Aki on 8.9.2017.
 */
@Component
public class DummyTestClassMultipleAnnotations {
    @Autowired
    public DummyTestClassMultipleAnnotations(DummyTestClass dummyTestClass) {

    }

    @Autowired
    public DummyTestClassMultipleAnnotations(DummyTestClass dummyTestClass, DependencyContextService dependencyContextService) {

    }
}
