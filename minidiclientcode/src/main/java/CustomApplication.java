import model.DependencyContext;
import tooling.MiniDi;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by akivv on 30.8.2017.
 */
public class CustomApplication {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        final DependencyContext dependencyContext = MiniDi.startApplication(CustomApplication.class);
    }
}
