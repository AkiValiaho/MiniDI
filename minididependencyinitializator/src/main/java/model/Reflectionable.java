package model;

import java.lang.reflect.Constructor;

/**
 * Created by Aki on 4.10.2017.
 */
public interface Reflectionable {
    boolean hasFieldInjection();

    boolean hasNoArgsConstructor(Constructor<?>[] declaredConstructors);
}
