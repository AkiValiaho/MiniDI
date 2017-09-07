package tooling;

import model.Dependency;
import model.DependencyContext;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by akivv on 5.9.2017.
 */
public class DependencyContextService {
    private final ReflectionInitializerTool reflectioninitializer;
    private final DependencyContext dependencyContext;

    public DependencyContextService(ReflectionInitializerTool reflectionInitializerTool, DependencyContext dependencyContext) {
        this.reflectioninitializer = reflectionInitializerTool;
        this.dependencyContext = dependencyContext;
    }

    private Dependency createDependency(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Dependency dependency = new Dependency(dependencyClass);
        dependency.initializeDependencyObject(reflectioninitializer);
        dependencyContext.addDependencyToMap(dependency);
        return dependency;
    }
}
