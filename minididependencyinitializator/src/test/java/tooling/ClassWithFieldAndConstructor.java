package tooling;

import annotations.Autowired;
import annotations.Component;

/**
 * Created by Aki on 28.9.2017.
 */
@Component
public class ClassWithFieldAndConstructor {
    private final DummyTestClassWithDependency dummyTestClassWithDependency;
    @Autowired
    private DummyTestClass dummyTestClass;
    @Autowired
    private ClassWithInjectionField classWithInjectionField;
    @Autowired
    public ClassWithFieldAndConstructor(DummyTestClassWithDependency dummyTestClassWithDependency) {
        this.dummyTestClassWithDependency = dummyTestClassWithDependency;
    }
}
