package model;

import annotations.Autowired;
import lombok.Getter;
import tooling.DependencyContextService;
import tooling.MultipleAnnotatedConstructorsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by akivv on 5.9.2017.
 */
public class Dependency {
    private DependencyContextService dependencyContextService;
    private Class<?> dependencyClass;
    private Map<Class<?>, Object> dependentParameters;
    @Getter
    private Object dependencyInstance;
    private Constructor<?> noArgsConstructor;
    private Constructor<?> argsConstructor;

    public Dependency(Class<?> dependencyClass, DependencyContextService dependencyContextService) {
        this.dependencyClass = dependencyClass;
        this.dependencyContextService = dependencyContextService;
    }


    /**
     * Initializes an object from the given dependency class
     */
    public void initializeDependencyObject(ReflectionTool reflectionInitializer) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        this.dependencyInstance = reflectionInitializer.initialize(this);
    }

    /**
     * Adds properties of this Dependency to the dependency context map
     *
     * @param dependencyContext
     */
    public void addDependencyToMap(DependencyContext dependencyContext) {
        dependencyContext.registerDependencyAttributes(dependencyClass, dependencyInstance);
    }

    boolean isLeafParameter() {
        //should have a no args constructor
        final Constructor<?>[] declaredConstructors = getDeclaredConstructors();
        return hasNoArgsConstructor(declaredConstructors);
    }

    private Constructor<?>[] getDeclaredConstructors() {
        return dependencyClass.getDeclaredConstructors();
    }

    private boolean hasNoArgsConstructor(Constructor<?>[] declaredConstructors) {
        final List<Constructor<?>> collect = Arrays.stream(declaredConstructors)
                .filter(constructor -> constructor.getParameterCount() == 0)
                .collect(Collectors.toList());
        if (collect.size() > 0) {
            //Has no args constructor, save it
            this.noArgsConstructor = collect.get(0);
            return true;
        }
        return false;
    }

    ;

    Object instantiateWithArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        findArgsConstructor();
        return argsConstructor.newInstance(dependentParameters.values().toArray());
    }

    private Constructor findArgsConstructor() {
        final Constructor<?>[] declaredConstructors = getDeclaredConstructors();
        return saveArgsConstructor(declaredConstructors);
    }

    private Constructor saveArgsConstructor(Constructor<?>[] declaredConstructors) {
        //Should have an autowired annotation present
        final List<Constructor<?>> collect = Arrays.stream(declaredConstructors)
                .filter(this::hasAutowiredAnnotation)
                .collect(Collectors.toList());
        if (collect.size() > 1) {
            throw new MultipleAnnotatedConstructorsException(dependencyClass);
        }
        this.argsConstructor = collect.get(0);
        return collect.get(0);
    }

    private boolean hasAutowiredAnnotation(Constructor<?> constructor) {
        return Arrays.stream(constructor.getAnnotations())
                .anyMatch(annotation -> annotation instanceof Autowired);
    }

    Object instantiateWithNoArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        this.dependencyInstance = noArgsConstructor.newInstance(null);
        return dependencyInstance;
    }

    boolean instantiateDependentParameters() {
        if (dependentParameters != null) {
            return true;
        }
        dependentParameters = new HashMap<>();
        instantiateParameters(dependencyContextService);
        return true;
    }

    private void instantiateParameters(DependencyContextService dependencyContextService) {
        Class<?>[] dependentParams = getDependentParamsFromArgsConstructor();
        List<Object> listOfInstantiatedObjects = dependencyContextService.instantiateListOfDependencies(dependentParams);
        addToMap(dependentParams, listOfInstantiatedObjects);
    }

    private void addToMap(Class<?>[] dependentParams, List<Object> listOfInstantiatedObjects) {
        final Iterator<Class<?>> dependentParamsIterator = Arrays.asList(dependentParams).iterator();
        final Iterator<Object> instantiatedObjectsIterator = listOfInstantiatedObjects.iterator();
        while (dependentParamsIterator.hasNext()) {
            final Class<?> nextdependentParam = dependentParamsIterator.next();
            final Object nextInstantiatedObject = instantiatedObjectsIterator.next();
            dependentParameters.put(nextdependentParam, nextInstantiatedObject);
        }
    }

    private Class<?>[] getDependentParamsFromArgsConstructor() {
        return getArgsConstructor()
                .map(Constructor::getParameterTypes).orElse(null);
    }

    private Optional<Constructor<?>> getArgsConstructor() {
        final List<Constructor<?>> collect = Arrays.stream(dependencyClass.getDeclaredConstructors())
                .filter(this::hasAutowiredAnnotation)
                .collect(Collectors.toList());
        if (collect.size() > 1) {
            throw new MultipleAnnotatedConstructorsException(dependencyClass);
        }
        return Optional.of(collect.get(0));
    }
}
