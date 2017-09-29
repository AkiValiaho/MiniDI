package model;

import java.util.List;

/**
 * Created by Aki on 28.9.2017.
 */
class DependentParams {
    private Class<?>[] fromFields;
    private Class<?>[] fromArgsConstructor;
    private List<Dependency> constructorInstances;
    private List<Dependency> fieldInstances;

    DependentParams getDependentParamsForClass(ReflectionRepresentation reflectionRepresentation, DependencyContextService dependencyContextService) {
        this.fromFields = dependentParamsFromFields(reflectionRepresentation);
        this.fromArgsConstructor = getDependentParamsFromArgsConstructor(reflectionRepresentation);
        if (fromArgsConstructor != null) {
            this.constructorInstances = dependencyContextService.instantiateListOfDependencies(fromArgsConstructor);
        }
        if (fromFields != null) {
            this.fieldInstances = dependencyContextService.instantiateListOfDependencies(fromFields);
        }
        return this;
    }

    private Class<?>[] dependentParamsFromFields(ReflectionRepresentation reflectionRepresentation) {
        return reflectionRepresentation.getDependentParamsFromFields();
    }

    private Class<?>[] getDependentParamsFromArgsConstructor(ReflectionRepresentation reflectionRepresentation) {
        return reflectionRepresentation.getParamTypesFromArgsConstructor();
    }

    Object[] getFieldInjectedInstances() {
        return instancesFromDependencies(fieldInstances);
    }

    private Object[] instancesFromDependencies(List<Dependency> listOfDependencies) {
        return listOfDependencies.stream()
                .map(Dependency::getDependencyInstance
                )
                .toArray();
    }

    Object[] getConstructorInjectedInstances() {
        return instancesFromDependencies(constructorInstances);
    }
}
