package tooling;

/**
 * Created by Aki on 8.9.2017.
 */
public class MultipleAnnotatedConstructorsException extends RuntimeException {
    public MultipleAnnotatedConstructorsException(Class<?> dependencyClass) {
        super("Class in question: " + dependencyClass.getCanonicalName());
    }
}
