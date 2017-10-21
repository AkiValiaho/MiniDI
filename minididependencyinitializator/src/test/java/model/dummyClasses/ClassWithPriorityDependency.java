package model.dummyClasses;

import annotations.Component;
import annotations.DependsOn;

/**
 * Created by Aki on 21.10.2017.
 */
@Component
@DependsOn(PriorityDependency.class)
public class ClassWithPriorityDependency {
}
