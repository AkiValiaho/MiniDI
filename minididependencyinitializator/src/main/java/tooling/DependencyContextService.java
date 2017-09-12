package tooling;

import model.Dependency;
import model.DependencyContext;
import model.ReflectionInitializerTool;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by akivv on 5.9.2017.
 */
public class DependencyContextService {
    private final ReflectionInitializerTool reflectioninitializer;
    private DependencyContext dependencyContext;

    public DependencyContextService(ReflectionInitializerTool reflectionInitializerTool, DependencyContext dependencyContext) {
        this.dependencyContext = dependencyContext;
        this.reflectioninitializer = reflectionInitializerTool;
    }

    Dependency createDependency(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        checkNotNull(dependencyClass);
        Dependency dependency = new Dependency(dependencyClass, this);
        dependency.initializeDependencyObject(reflectioninitializer);
        dependencyContext.addDependencyToMap(dependency);
        return dependency;
    }

    public List<Object> instantiateListOfDependencies(Class<?>[] dependentParams) {
return         Arrays.stream(dependentParams)
                .map(this::createDependencyWithExceptionSafety
               )
                .collect(Collectors.toList());
    }

    private Dependency createDependencyWithExceptionSafety(Class<?> param) {
        try {
            return createDependency(param);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            injectionError(e);
        }
        return null;
    }

    private void injectionError(ReflectiveOperationException e) {
        System.out.println(e);
        System.exit(1);
    }
}
