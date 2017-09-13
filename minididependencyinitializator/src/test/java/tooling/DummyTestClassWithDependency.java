package tooling;

import annotations.Autowired;
import annotations.Component;
import lombok.Getter;

/**
 * Created by Aki on 8.9.2017.
 */
@Component
public class DummyTestClassWithDependency {
    @Getter
    private final DummyTestClass dummyTestClass;

    @Autowired
    public DummyTestClassWithDependency(DummyTestClass dummyTestClass) {
        this.dummyTestClass = dummyTestClass;
    }

    public DummyTestClass getDependency() {
        return dummyTestClass;
    }
}
