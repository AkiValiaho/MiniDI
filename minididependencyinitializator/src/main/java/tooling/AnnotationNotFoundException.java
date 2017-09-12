package tooling;

import java.lang.reflect.Constructor;

/**
 * Created by Aki on 8.9.2017.
 */
public class AnnotationNotFoundException extends RuntimeException {
    public AnnotationNotFoundException(Constructor<?> constructor) {
    }
}
