package model;

import lombok.Getter;
import lombok.Setter;
import tooling.DependencyContextService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
    private ReflectionRepresentation reflectionRepresentation;

    public Dependency(Class<?> dependencyClass, DependencyContextService dependencyContextService, ReflectionTool reflectionInitializer) {
        this.dependencyClass = dependencyClass;
        this.dependencyContextService = dependencyContextService;
        this.reflectionInitializer = reflectionInitializer;
    }


    /**
     * Initializes an object from the given dependency class
     */
    public void initializeDependencyObject() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        this.reflectionRepresentation = reflectionInitializer.getReflectionRepresentation(dependencyClass);
        this.dependencyInstance = reflectionInitializer.initialize(this);
    }

    /**
     * Adds properties of this Dependency to the dependency context map
     *
     * @param dependencyContext
     */
    void addDependencyToMap(DependencyContext dependencyContext) {
        dependencyContext.registerDependencyAttributes(dependencyClass, dependencyInstance);
    }

    Object instantiate() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (isLeafParameter()) {
            //TODO Simplify this logic a bit (move noargs constructor search into the dependency model object
            return instantiateWithNoArgsConstructor();
        }
        //It's not a leaf parameter
        if (instantiateDependentParameters()) {
            //We can now instantiate from the constructor
            return instantiateFromArgsConstructor();
        }
        return null;
    }

    private Object instantiateFromArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return instantiateWithArgsConstructor();
    }

    private Object instantiateWithArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Optional<Constructor> argsConstructor = reflectionRepresentation.getArgsConstructor();
        final Object[] objects = dependentParameters.values().stream()
                .map(Dependency::getDependencyInstance)
                .toArray();
        if (argsConstructor.isPresent()) {
            return argsConstructor.get().newInstance(objects);
        }
        return null;
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
        return reflectionRepresentation.hasInjectedFields();

    }

    private Object instantiateWithNoArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Optional<Constructor> noArgsConstructor = reflectionRepresentation.getNoArgsConstructor();
        if (noArgsConstructor.isPresent()) {
            this.dependencyInstance = noArgsConstructor.get().newInstance(null);
        }
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
        return reflectionRepresentation.getDependentParamsFromFields();
    }

    private Class<?>[] getDependentParamsFromArgsConstructor() {
        return reflectionRepresentation.getParamTypesFromArgsConstructor();
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

    private boolean hasNoArgsConstructor(Constructor<?>[] declaredConstructors) {
        return reflectionRepresentation.getNoArgsConstructor().isPresent();

    }

}
