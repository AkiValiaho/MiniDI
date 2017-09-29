package model;

import tooling.MultipleAnnotatedConstructorsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

class ReflectionRepresentation {
    private final Class<?> dependencyClass;
    private final ReflectionTool reflectionInitializer;

    ReflectionRepresentation(Class<?> dependencyClass, ReflectionTool reflectionInitializer) {
        this.dependencyClass = dependencyClass;
        this.reflectionInitializer = reflectionInitializer;
    }


    boolean hasInjectedFields() {
        return Arrays.stream(dependencyClass.getDeclaredFields())
                .anyMatch(reflectionInitializer::hasAutowiredAnnotation);
    }

    private boolean isArgsConstructor(Constructor<?> constructor) {
        return constructor.getParameterCount() != 0;
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

    Class<?>[] getDependentParamsFromFields() {
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
                .filter(reflectionInitializer::hasAutowiredAnnotation)
                .toArray(Field[]::new);
    }

    private Field[] getFields() {
        return dependencyClass.getDeclaredFields();
    }

    private Class<?> mapFieldToClass(Field field) {
        return field.getType();
    }

    ReflectionRepresentation represent() {
        return this;
    }

    Class<?>[] getParamTypesFromArgsConstructor() {
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
                .filter(reflectionInitializer::hasAutowiredAnnotation)
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

    void injectFields(Object o, Object[] fieldInjectedInstances) {
        final Field[] declaredFields = o.getClass().getDeclaredFields();
        setFields(o, declaredFields, fieldInjectedInstances);
    }

    private void setFields(Object o, Field[] declaredFields, Object[] fieldInjectedInstances) {
        List<MatchedPair> matchedPairs = filterAndOrderInstances(declaredFields, fieldInjectedInstances);
        matchedPairs.stream()
                    .forEach(matchedPair -> {
                        try {
                            setField(o, matchedPair);
                        } catch (IllegalAccessException e) {
                            System.exit(1);
                        }
                    });

    }

    private void setField(Object o, MatchedPair matchedPair) throws IllegalAccessException {
        matchedPair.setFieldToInstance(o);
    }

    private List<MatchedPair> filterAndOrderInstances(Field[] declaredFields, Object[] fieldInjectedInstances) {
        return new MatchedPair(declaredFields, fieldInjectedInstances)
                .matchPairs();
    }

}
