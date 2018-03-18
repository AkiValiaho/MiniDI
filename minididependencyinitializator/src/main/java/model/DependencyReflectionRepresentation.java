package model;

import annotations.DependsOn;
import annotations.PostConstruct;
import tooling.MultipleAnnotatedConstructorsException;
import tooling.ReflectionComponent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DependencyReflectionRepresentation extends ReflectionComponent {

    public DependencyReflectionRepresentation(Class<?> dependencyClass) {
        super(dependencyClass);
    }


    boolean hasInjectedFields() {
        return Arrays.stream(dependencyClass.getDeclaredFields())
                .anyMatch(this::hasAutowiredAnnotation);
    }

    Optional<Constructor> getNoArgsConstructor() {
        return filterNoArgsConstructor(getConstructors());
    }

    private Constructor[] getConstructors() {
        return dependencyClass.getDeclaredConstructors();
    }

    private Optional<Constructor> filterNoArgsConstructor(Constructor[] noArgsConstructor) {
        return Arrays.stream(noArgsConstructor)
                .filter(this::isNoArgsConstructor)
                .findFirst();
    }

    private boolean isNoArgsConstructor(Constructor constructor) {
        return constructor.getParameterCount() == 0;
    }

    public Class<?>[] getDependentParamsFromFields() {
        Field[] fields = filterAutowiredFields(getFields());
        return mapFieldsToClasses(fields);
    }


    private Class<?>[] mapFieldsToClasses(Field[] fields) {
        return Arrays.stream(fields)
                .map(this::mapFieldToClass)
                .toArray(Class<?>[]::new);
    }

    private Field[] filterAutowiredFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(this::hasAutowiredAnnotation)
                .toArray(Field[]::new);
    }

    private Field[] getFields() {
        return dependencyClass.getDeclaredFields();
    }

    private Class<?> mapFieldToClass(Field field) {
        return field.getType();
    }

    public DependencyReflectionRepresentation represent() {
        return this;
    }

    public Class<?>[] getParamTypesFromArgsConstructor() {
        Optional<Constructor> argsConstructor = getArgsConstructor();
        return argsConstructor.isPresent() ? argsConstructor.get().getParameterTypes() : null;
    }

    Optional<Constructor> getArgsConstructor() {
        Constructor[] argsConstructors = filterArgsConstructor(getConstructors());
        Constructor[] annotatedArgsConstructors = filterAnnotatedArgsConstructors(argsConstructors);
        throwExceptionIfMoreThanOneAnnotatedConstructor(annotatedArgsConstructors);
        return optionalFromConstructorArray(annotatedArgsConstructors);
    }

    private Optional<Constructor> optionalFromConstructorArray(Constructor[] annotatedArgsConstructors) {
        return annotatedArgsConstructors.length == 0
                ? Optional.empty()
                : Optional.of(annotatedArgsConstructors[0]);
    }

    private void throwExceptionIfMoreThanOneAnnotatedConstructor(Constructor[] annotatedArgsConstructors) {
        if (annotatedArgsConstructors.length > 1) {
            throw new MultipleAnnotatedConstructorsException(dependencyClass);
        }
    }

    private Constructor[] filterAnnotatedArgsConstructors(Constructor<?>[] argsConstructors) {
        List<Constructor<?>> collect = Arrays.stream(argsConstructors)
                .filter(this::hasAutowiredAnnotation)
                .collect(Collectors.toList());
        return toConstructorArray(collect);

    }

    private Constructor[] filterArgsConstructor(Constructor<?>[] listOfConstructors) {
        List<Constructor<?>> collect = Arrays.stream(listOfConstructors)
                .filter(this::isArgsConstructor)
                .collect(Collectors.toList());
        return toConstructorArray(collect);
    }

    private Constructor[] toConstructorArray(List<Constructor<?>> collect) {
        return collect.toArray(new Constructor[collect.size()]);
    }

    void injectFields(Object o, Object[] fieldInjectedInstances) throws IllegalAccessException {
        final Field[] declaredFields = o.getClass().getDeclaredFields();
        setFields(o, declaredFields, fieldInjectedInstances);
    }

    private void setFields(Object instanceToInjectTo, Field[] declaredFields, Object[] fieldInjectedInstances) throws IllegalAccessException {
        new MatchedPairInjector(declaredFields, fieldInjectedInstances, instanceToInjectTo);
    }

    Optional<Method> getPostConstructMethod() {
        return Arrays.stream(dependencyClass.getDeclaredMethods())
                .filter(method -> hasAnnotation(method, PostConstruct.class))
                .findFirst();
    }

    public Optional<Class<?>> getPriorityDependencyClass() {
        final Optional<DependsOn> first = findDependsOnAnnotation(DependsOn.class);
        return first.map(DependsOn::value);
    }


}
