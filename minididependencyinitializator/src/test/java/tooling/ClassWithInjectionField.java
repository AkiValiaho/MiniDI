package tooling;

import annotations.Autowired;
import annotations.Component;

@Component
public class ClassWithInjectionField {
    @Autowired
    private DummyTestClass dummyTestClass;
}
