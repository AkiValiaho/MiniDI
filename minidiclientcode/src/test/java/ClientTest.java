import model.DependencyContext;
import org.junit.Test;
import tooling.MiniDi;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 13.9.2017.
 */
public class ClientTest {
    @Test
    public void checkContextInitializesAllDependencies() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        final DependencyContext dependencyContext = MiniDi.startApplication(CustomApplication.class);
        final Optional<Object> dependency = dependencyContext.getDependency(ClientCodeDependency.class);
        assertTrue(dependency.isPresent());

    }

    @Test
    public void checkContextInitializesFieldInjections() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        DependencyContext dependencyContext = MiniDi.startApplication(CustomApplication.class);
        //There is one dependency that is injected with fields, check it's present
        Optional<Object> dependencyWithFieldInjection = dependencyContext.getDependency(ClientFieldCodeDependency.class);
        assertTrue(dependencyWithFieldInjection.isPresent());
    }
}
