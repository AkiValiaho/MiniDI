package model;

import annotations.Autowired;
import annotations.Component;

/**
 * Created by Aki on 6.10.2017.
 */
@Component
class CyclicClassTransitiveC {
    @Autowired
    private CyclicClassTransitiveA cyclicClassTransitiveA;
}
