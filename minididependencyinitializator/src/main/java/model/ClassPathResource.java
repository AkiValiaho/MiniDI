package model;

import tooling.CyclicDependencyException;
import tooling.ReflectionTool;

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

    public void convertResourcesToDependencies(ReflectionTool reflectionTool, DependencyContextComponent dependencyContextService) {
        final List<ClassPathResource> classPathResources = scanClassPath(reflectionTool);
        classPathResources.forEach(resource -> {
            try {
                convertResourceToDependency(dependencyContextService, resource);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | CyclicDependencyException e) {
                System.exit(1);
            }
        });
    }

    private void convertResourceToDependency(DependencyContextComponent dependencyContextService, ClassPathResource classPathResource) throws IllegalAccessException, InvocationTargetException, InstantiationException, CyclicDependencyException {
        dependencyContextService.createDependenciesFromResource(classPathResource.getDependencyClass());
    }

    private Class<?> getDependencyClass() {
        return startClass;
    }

    private List<ClassPathResource> scanClassPath(ReflectionTool reflectionTool) {
        return reflectionTool.findClassPathResources(startClass);
    }
}
