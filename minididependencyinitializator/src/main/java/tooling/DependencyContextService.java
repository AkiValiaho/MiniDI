package tooling;

import model.Dependency;
import model.DependencyContext;
import model.ReflectionTool;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by akivv on 5.9.2017.
 */
public class DependencyContextService {
    private final ReflectionTool reflectioninitializer;
    private DependencyContext dependencyContext;

    DependencyContextService(ReflectionTool reflectionTool, DependencyContext dependencyContext) {
        this.dependencyContext = dependencyContext;
        this.reflectioninitializer = reflectionTool;
    }

    public List<Dependency> instantiateListOfDependencies(Class<?>[] dependentParams) {
        return Arrays.stream(dependentParams)
                .map(this::createDependencyWithExceptionSafety
                )
                .collect(Collectors.toList());
    }

    private Dependency createDependencyWithExceptionSafety(Class<?> param) {
        try {
            return createDependenciesFromResource(param);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            injectionError(e);
        }
        return null;
    }

    public Dependency createDependenciesFromResource(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        checkNotNull(dependencyClass);
        Dependency dependency = new Dependency(dependencyClass, this, reflectioninitializer);
        dependency.initializeDependencyObject();
        dependencyContext.addDependencyToMap(dependency);
        return dependency;
    }

    private void injectionError(ReflectiveOperationException e) {
        System.out.println(e);
        System.exit(1);
    }


}
