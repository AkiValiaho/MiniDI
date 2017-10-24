package tooling;

import annotations.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Created by Aki on 4.10.2017.
 */
public abstract class ReflectionComponent {
    protected boolean hasAutowiredAnnotation(AnnotatedElement reflectionMember) {
        return reflectionMember.isAnnotationPresent(Autowired.class);
    }

    protected boolean hasAnnotation(AnnotatedElement reflectionMember, Class<? extends Annotation> annotation) {
        return reflectionMember.isAnnotationPresent(annotation);
    }
}
