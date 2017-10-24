package model.dummyClasses;

import annotations.Component;
import annotations.PostConstruct;

/**
 * Created by Aki on 24.10.2017.
 */
@Component
public class ClassWithPostConstructWrongParameters {
    //This construct is not allowed
    @PostConstruct
    public void init(String hello) {
        System.out.println("should never happen");
    }
}
