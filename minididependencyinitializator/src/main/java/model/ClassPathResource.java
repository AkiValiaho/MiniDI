package model;

import tooling.DependencyContextService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Aki on 12.9.2017.
 */
public class ClassPathResource {
    private final Class<?> startClass;

    public ClassPathResource(Class<?> startClass) {
        this.startClass = startClass;
    }

    private void convertResourceToDependency(DependencyContextService dependencyContextService, ClassPathResource classPathResource) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        dependencyContextService.createDependenciesFromResource(classPathResource.getDependencyClass());
    }

    private List<ClassPathResource> scanClassPath(ReflectionTool reflectionTool) {
        return reflectionTool.findClassPathResources(startClass);
    }

    public void convertResourcesToDependencies(ReflectionTool reflectionTool, DependencyContextService dependencyContextService) {
        final List<ClassPathResource> classPathResources = scanClassPath(reflectionTool);
        classPathResources.forEach(resource -> {
            try {
                convertResourceToDependency(dependencyContextService, resource);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                System.exit(1);
            }
        });
    }

    public Class<?> getDependencyClass() {
        return startClass;
    }
}
