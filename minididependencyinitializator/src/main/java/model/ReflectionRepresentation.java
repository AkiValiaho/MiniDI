package model;

import tooling.MultipleAnnotatedConstructorsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
                .filter(constructor -> isNoArgsConstructor(constructor))
                .findFirst();
    }

    private boolean isNoArgsConstructor(Constructor constructor) {
        return constructor.getParameterCount() == 0;
    }

    Class<?>[] getDependentParamsFromFields() {
        return filterAutowiredFields(getFields());
    }

    private Class<?>[] filterAutowiredFields(Field[] fields) {
        return (Class<?>[]) Arrays.stream(fields)
                .filter(reflectionInitializer::hasAutowiredAnnotation)
                .toArray();
    }

    private Field[] getFields() {
        return dependencyClass.getFields();
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
        if (annotatedArgsConstructors.length == 0) {
            return Optional.empty();
        } else {
            return Optional.of(annotatedArgsConstructors[0]);
        }
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

    private Constructor[] toConstructorArray(List<Constructor<?>> collect) {
        return collect.toArray(new Constructor[collect.size()]);
    }

    private Constructor[] filterArgsConstructor(Constructor<?>[] listOfConstructors) {
        List<Constructor<?>> collect = Arrays.stream(listOfConstructors)
                .filter(this::isArgsConstructor)
                .collect(Collectors.toList());
        return toConstructorArray(collect);
    }
}
