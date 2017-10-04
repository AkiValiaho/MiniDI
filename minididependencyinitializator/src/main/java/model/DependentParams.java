package model;

import tooling.DependencyContextComponent;

import java.util.List;

/**
 * Created by Aki on 28.9.2017.
 */
class DependentParams {
    private Class<?>[] fromFields;
    private Class<?>[] fromArgsConstructor;
    private List<Dependency> constructorInstances;
    private List<Dependency> fieldInstances;

    DependentParams getDependentParamsForClass(DependencyReflectionRepresentation dependencyReflectionRepresentation, DependencyContextComponent dependencyContextService) {
        this.fromFields = dependentParamsFromFields(dependencyReflectionRepresentation);
        this.fromArgsConstructor = getDependentParamsFromArgsConstructor(dependencyReflectionRepresentation);
        if (fromArgsConstructor != null) {
            this.constructorInstances = dependencyContextService.instantiateListOfDependencies(fromArgsConstructor);
        }
        if (fromFields != null) {
            this.fieldInstances = dependencyContextService.instantiateListOfDependencies(fromFields);
        }
        return this;
    }

    private Class<?>[] dependentParamsFromFields(DependencyReflectionRepresentation dependencyReflectionRepresentation) {
        return dependencyReflectionRepresentation.getDependentParamsFromFields();
    }

    private Class<?>[] getDependentParamsFromArgsConstructor(DependencyReflectionRepresentation dependencyReflectionRepresentation) {
        return dependencyReflectionRepresentation.getParamTypesFromArgsConstructor();
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
