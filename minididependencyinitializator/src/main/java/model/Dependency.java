package model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by akivv on 5.9.2017.
 */
public class Dependency {
    private DependencyContextService dependencyContextService;
    private Class<?> dependencyClass;
    private Map<Class<?>, Dependency> dependentParameters;
    @Getter
    @Setter
    private Object dependencyInstance;
    private ReflectionRepresentation reflectionRepresentation;

    Dependency(Class<?> dependencyClass, DependencyContextService dependencyContextService, ReflectionRepresentation reflectionRepresentation) {
        this.dependencyClass = dependencyClass;
        this.dependencyContextService = dependencyContextService;
        this.reflectionRepresentation = reflectionRepresentation;
    }


    /**
     * Adds properties of this Dependency to the dependency context map
     *
     * @param dependencyContext
     */
    void addDependencyToMap(DependencyContext dependencyContext) {
        dependencyContext.registerDependencyAttributes(dependencyClass, dependencyInstance);
    }

    void instantiate() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (isLeafParameter()) {
            dependencyInstance = instantiateWithNoArgsConstructor();
            return;
        }
        //It's not a leaf parameter
        if (instantiateDependentParameters()) {
            //We can now instantiateDependency from the constructor
            Object o = instantiateFromArgsConstructor();
            dependencyInstance = o;
            if (o == null) {
                //Contains only field injections
                instantiateWithNoArgsConstructor();
            }
        }
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
        //should have a no args constructor and no autowired fields
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

    private boolean hasNoArgsConstructor(Constructor<?>[] declaredConstructors) {
        return reflectionRepresentation.getNoArgsConstructor().isPresent();

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
        instantiateParameters();
        return true;
    }

    private void instantiateParameters() {
        Class<?>[] dependentParams = getDependentParamsFromFieldsAndConstructor();
        List<Dependency> listOfInstantiatedObjects = dependencyContextService.instantiateListOfDependencies(dependentParams);
        addToMap(dependentParams, listOfInstantiatedObjects);
    }

    private Class<?>[] getDependentParamsFromFieldsAndConstructor() {
        Class<?>[] dependentParamsFromArgsConstructor = getDependentParamsFromArgsConstructor();
        //TODO Add check for field injections here
        return dependentParamsFromArgsConstructor != null
                ? dependentParamsFromArgsConstructor
                : dependentParamsFromFields();
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
}
