package tooling;

import annotations.Autowired;
import annotations.Component;

/**
 * Created by Aki on 13.9.2017.
 */
@Component
public class RecursiveDummyTestClass {
    private final DummyTestClassWithDependency dummyTestClassWithDependency;

    @Autowired
    public RecursiveDummyTestClass(DummyTestClassWithDependency dummyTestClassWithDependency) {
        this.dummyTestClassWithDependency = dummyTestClassWithDependency;
    }
}
