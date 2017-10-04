package model;

import annotations.Autowired;
import annotations.Component;

/**
 * Created by Aki on 4.10.2017.
 */
@Component
public class CyclicClassA {
    @Autowired
    CyclicClassB cyclicClassB;
}
