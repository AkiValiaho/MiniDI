package model;

import tooling.ReflectionTool;
import tooling.ReflectionToolSet;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
public class ClassPathResourceService extends DependencyContextComponent {

    public ClassPathResourceService(DependencyComponentFactory dependencyComponentFactory, ReflectionTool reflectionTool, DependencyContext dependencyContext, ReflectionToolSet reflectionToolSet) {
        super(dependencyComponentFactory, reflectionTool, dependencyContext, reflectionToolSet);
    }

    private void createDependencyContextClassPath(Class<?> startClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        new ClassPathResource(startClass)
                .convertResourcesToDependencies(getReflectioninitializer(), this);

    }

    public void createDependenciesFromClassPath(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        createDependencyContextClassPath(startClass);
    }
}
