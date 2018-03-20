package model.components;

import lombok.Getter;
import model.Dependency;
import model.DependencyContext;
import tooling.CyclicDependencyException;
import tooling.ReflectionTool;
import tooling.ReflectionToolSet;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Aki on 4.10.2017.
 */
public abstract class DependencyContextComponent {
    DependencyComponentFactory<Dependency> dependencyComponentFactory;
    DependencyContext dependencyContext;
    @Getter
    private ReflectionTool reflectioninitializer;
    private ReflectionToolSet reflectionToolSet;

    public DependencyContextComponent(DependencyComponentFactory<Dependency> dependencyComponentFactory, ReflectionTool reflectionTool, DependencyContext dependencyContext, ReflectionToolSet reflectionToolSet) {
        this.dependencyComponentFactory = dependencyComponentFactory;
        this.reflectioninitializer = reflectionTool;
        this.dependencyContext = dependencyContext;
        this.reflectionToolSet = reflectionToolSet;
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
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | CyclicDependencyException e) {
            injectionError(e);
        }
        return null;
    }

    public Dependency createDependenciesFromResource(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException, CyclicDependencyException {
        checkNotNull(dependencyClass);
        Dependency dependency = createDependencyObject(dependencyClass);
        dependencyContext.addDependencyToMap(dependency);
        return dependency;
    }

    private Dependency createDependencyObject(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException, CyclicDependencyException {
        return dependencyComponentFactory.createDependencyComponent(dependencyClass, this, reflectionToolSet);
    }

    private void injectionError(Exception e) {
        System.out.println(e);
        System.exit(1);
    }

    public void logDependencyInstantiation(Class<?> dependencyClass) {
        dependencyContext.logDependency(dependencyClass);
    }
}
