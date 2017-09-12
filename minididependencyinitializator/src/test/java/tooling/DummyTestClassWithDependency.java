package tooling;

import annotations.Autowired;

/**
 * Created by Aki on 8.9.2017.
 */
public class DummyTestClassWithDependency {
    private final DummyTestClass dummyTestClass;

    @Autowired
    public DummyTestClassWithDependency(DummyTestClass dummyTestClass) {
        this.dummyTestClass = dummyTestClass;
    }

    public DummyTestClass getDependency() {
        return dummyTestClass;
    }
}
