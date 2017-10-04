package tooling;

import lombok.Getter;
import model.Dependency;
import model.DependencyContext;
import model.DependencyReflectionRepresentation;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Aki on 4.10.2017.
 */
public abstract class DependencyContextComponent {
    DependencyFactoryComponent dependencyFactory;
    DependencyContext dependencyContext;
    @Getter
    private ReflectionTool reflectioninitializer;

    DependencyContextComponent(DependencyFactoryComponent dependencyFactory, ReflectionTool reflectionTool, DependencyContext dependencyContext) {
        this.dependencyFactory = dependencyFactory;
        this.reflectioninitializer = reflectionTool;
        this.dependencyContext = dependencyContext;
    }

    public Dependency createDependenciesFromResource(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException, CyclicDependencyException {
        checkNotNull(dependencyClass);
        Dependency dependency = createDependencyObject(dependencyClass);
        dependencyContext.addDependencyToMap(dependency);
        return dependency;
    }

    private Dependency createDependencyObject(Class<?> dependencyClass) throws IllegalAccessException, InstantiationException, InvocationTargetException, CyclicDependencyException {
        DependencyReflectionRepresentation dependencyReflectionRepresentation = reflectioninitializer.getReflectionRepresentation(dependencyClass);
        return dependencyFactory.createDependency(dependencyClass, this, dependencyReflectionRepresentation);
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

    private void injectionError(Exception e) {
        System.out.println(e);
        System.exit(1);
    }
}
