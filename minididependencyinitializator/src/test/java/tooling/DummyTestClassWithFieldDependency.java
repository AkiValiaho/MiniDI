package tooling;

import annotations.Autowired;
import annotations.Component;

@Component
public class DummyTestClassWithFieldDependency {
    @Autowired
    DummyTestClass dummyTestClass;
}
