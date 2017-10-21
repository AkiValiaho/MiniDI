package tooling;

import model.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
class ClassPathResourceService extends DependencyContextComponent {

    ClassPathResourceService(DependencyComponentFactory dependencyComponentFactory, ReflectionTool reflectionTool, DependencyContext dependencyContext) {
        super(dependencyComponentFactory, reflectionTool, dependencyContext);
    }

    private void createDependencyContextClassPath(Class<?> startClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        new ClassPathResource(startClass)
                .convertResourcesToDependencies(getReflectioninitializer(), this);

    }

    void createDependenciesFromClassPath(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        createDependencyContextClassPath(startClass);
    }
}
