package model;

import annotations.Autowired;

import java.lang.reflect.AnnotatedElement;

/**
 * Created by Aki on 4.10.2017.
 */
abstract class ReflectionComponent {
    boolean hasAutowiredAnnotation(AnnotatedElement reflectionMember) {
        return reflectionMember.isAnnotationPresent(Autowired.class);
    }
}
