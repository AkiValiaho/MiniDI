package tooling;

import annotations.Autowired;
import annotations.Component;
import lombok.Getter;
import model.dummyClasses.DummyTestClassB;

/**
 * Created by Aki on 8.9.2017.
 */
@Component
public class DummyTestClassWithDependency {
    @Getter
    private final DummyTestClassB dummyTestClass;

    @Autowired
    public DummyTestClassWithDependency(DummyTestClassB dummyTestClass) {
        this.dummyTestClass = dummyTestClass;
    }

    public DummyTestClassB getDependency() {
        return dummyTestClass;
    }
}
