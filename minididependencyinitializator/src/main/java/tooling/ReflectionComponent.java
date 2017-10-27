package tooling;

import annotations.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Aki on 4.10.2017.
 */
public abstract class ReflectionComponent {


    protected final Class<?> dependencyClass;

    protected ReflectionComponent(Class<?> dependencyClass) {
        this.dependencyClass = dependencyClass;
    }

    protected boolean isArgsConstructor(Constructor<?> constructor) {
        return constructor.getParameterCount() != 0;
    }

    protected <T extends Annotation> Optional<T> findDependsOnAnnotation(Class<T> annotation) {
        final Optional<Annotation> first = Arrays.stream(getClassAnnotations())
                .filter(element -> element.annotationType().equals(annotation))
                .findFirst();
        return (Optional<T>) first;
    }

    private Annotation[] getClassAnnotations() {
        return dependencyClass.getAnnotations();
    }

    protected Method[] getMethods() {
        return dependencyClass.getDeclaredMethods();
    }

    protected boolean hasAutowiredAnnotation(AnnotatedElement reflectionMember) {
        return reflectionMember.isAnnotationPresent(Autowired.class);
    }

    protected boolean hasAnnotation(AnnotatedElement reflectionMember, Class<? extends Annotation> annotation) {
        return reflectionMember.isAnnotationPresent(annotation);
    }
}
