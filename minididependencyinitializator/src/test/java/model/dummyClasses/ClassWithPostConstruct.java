package model.dummyClasses;

import annotations.Component;
import annotations.PostConstruct;

/**
 * Created by Aki on 24.10.2017.
 */
@Component
public class ClassWithPostConstruct {
    private String something;

    @PostConstruct
    public void callThisMethod() {
        this.something = "helloWorld";
    }
}
