package tooling;

import model.ClassPathResource;
import model.ReflectionTool;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
class ClassPathResourceService {
    private DependencyContextService dependencyContextService;
    private ReflectionTool reflectionTool;

    ClassPathResourceService(DependencyContextService dependencyContextService, ReflectionTool reflectionTool) {
        this.dependencyContextService = dependencyContextService;
        this.reflectionTool = reflectionTool;
    }

    private void createDependencyContextClassPath(Class<?> startClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        new ClassPathResource(startClass)
                .convertResourcesToDependencies(reflectionTool, dependencyContextService);

    }

    void createDependenciesFromClassPath(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        createDependencyContextClassPath(startClass);
    }
}
