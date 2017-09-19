package model;

import lombok.Getter;
import lombok.Setter;
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
    private final ReflectionTool reflectionInitializer;
    private DependencyContextService dependencyContextService;
    private Class<?> dependencyClass;
    private Map<Class<?>, Dependency> dependentParameters;
    @Getter
    @Setter
    private Object dependencyInstance;
    private Constructor<?> noArgsConstructor;
    private Constructor<?> argsConstructor;

    public Dependency(Class<?> dependencyClass, DependencyContextService dependencyContextService, ReflectionTool reflectionInitializer) {
        this.dependencyClass = dependencyClass;
        this.dependencyContextService = dependencyContextService;
        this.reflectionInitializer = reflectionInitializer;
    }


    /**
     * Initializes an object from the given dependency class
     */
    public void initializeDependencyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException {
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
        return fullfillsLeafParameterCriteria(declaredConstructors);
    }

    private Constructor<?>[] getDeclaredConstructors() {
        return dependencyClass.getDeclaredConstructors();
    }

    private boolean fullfillsLeafParameterCriteria(Constructor<?>[] declaredConstructors) {
        return hasNoArgsConstructor(declaredConstructors) &&
                hasNoFieldInjections();

    }

    private boolean hasNoFieldInjections() {
        return !hasFieldInjection();
    }

    private boolean hasFieldInjection() {
        return Arrays.stream(dependencyClass.getDeclaredFields())
                .anyMatch(reflectionInitializer::hasAutowiredAnnotation);
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
        final Object[] objects = dependentParameters.values().stream()
                .map(Dependency::getDependencyInstance)
                .toArray();
        return argsConstructor.newInstance(objects);
    }

    private Constructor findArgsConstructor() {
        final Constructor<?>[] declaredConstructors = getDeclaredConstructors();
        return saveArgsConstructor(declaredConstructors);
    }

    private Constructor saveArgsConstructor(Constructor<?>[] declaredConstructors) {
        //Should have an autowired annotation present
        final List<Constructor<?>> collect = Arrays.stream(declaredConstructors)
                .filter(reflectionInitializer::hasAutowiredAnnotation)
                .collect(Collectors.toList());
        multipleConstructorsExceptionCheck(collect);
        this.argsConstructor = collect.get(0);
        return collect.get(0);
    }

    private void multipleConstructorsExceptionCheck(List<Constructor<?>> collect) {
        if (collect.size() > 1) {
            throw new MultipleAnnotatedConstructorsException(dependencyClass);
        }
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
        //TODO We are missing a concept here, refactor HierarchichalDependency model from this class to represent dependencies with other dependencies!
        Class<?>[] dependentParams = getDependentParamsFromFieldsOrConstructor();
        List<Dependency> listOfInstantiatedObjects = dependencyContextService.instantiateListOfDependencies(dependentParams);
        addToMap(dependentParams, listOfInstantiatedObjects);
    }

    private Class<?>[] getDependentParamsFromFieldsOrConstructor() {
        Class<?>[] dependentParamsFromArgsConstructor = getDependentParamsFromArgsConstructor();
        if (dependentParamsFromArgsConstructor != null) {
            return dependentParamsFromArgsConstructor;
        } else {
            return dependentParamsFromFields();
        }
    }

    private Class<?>[] dependentParamsFromFields() {
    }

    private Class<?>[] getDependentParamsFromArgsConstructor() {
        return reflectionInitializer.getArgsConstructor(dependencyClass)
                .map(Constructor::getParameterTypes).orElse(null);
    }

    private void addToMap(Class<?>[] dependentParams, List<Dependency> listOfInstantiatedObjects) {
        final Iterator<Class<?>> dependentParamsIterator = Arrays.asList(dependentParams).iterator();
        final Iterator<Dependency> instantiatedObjectsIterator = listOfInstantiatedObjects.iterator();
        while (dependentParamsIterator.hasNext()) {
            final Class<?> nextdependentParam = dependentParamsIterator.next();
            final Dependency nextInstantiatedObject = instantiatedObjectsIterator.next();
            dependentParameters.put(nextdependentParam, nextInstantiatedObject);
        }
    }
}
