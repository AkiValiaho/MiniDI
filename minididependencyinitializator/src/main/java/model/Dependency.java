package model;

import lombok.Getter;
import lombok.Setter;
import tooling.DependencyContextComponent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * Created by akivv on 5.9.2017.
 */
public class Dependency implements Reflectionable {
    private DependencyContextComponent dependencyContextService;
    private Class<?> dependencyClass;
    private DependentParams dependentParameters;
    @Getter
    @Setter
    private Object dependencyInstance;
    private DependencyReflectionRepresentation dependencyReflectionRepresentation;

    public Dependency(Class<?> dependencyClass, DependencyContextComponent dependencyContextService, DependencyReflectionRepresentation dependencyReflectionRepresentation) {
        this.dependencyClass = dependencyClass;
        this.dependencyContextService = dependencyContextService;
        this.dependencyReflectionRepresentation = dependencyReflectionRepresentation;
    }


    /**
     * Adds properties of this Dependency to the dependency context map
     *
     * @param dependencyContext
     */
    void addDependencyToMap(DependencyContext dependencyContext) {
        dependencyContext.registerDependencyAttributes(dependencyClass, dependencyInstance);
    }

    public void instantiate() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (isLeafParameter()) {
            dependencyInstance = instantiateWithNoArgsConstructor();
            return;
        }
        //It's not a leaf parameter
        if (instantiateDependentParameters()) {
            Object o = instantiateWithNoArgsIfOnlyFieldDependencies();
            //We can now instantiateDependency from the constructor
            if (o == null) {
                o = instantiateFromArgsConstructor();
            }
            injectFields(o);
            dependencyInstance = o;
        }
    }

    private Object instantiateWithNoArgsIfOnlyFieldDependencies() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (dependencyReflectionRepresentation.getNoArgsConstructor().isPresent()) {
            return dependencyReflectionRepresentation.getNoArgsConstructor().get().newInstance(null);
        }
        return null;
    }

    private Object instantiateFromArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return instantiateWithArgsConstructor();
    }

    private Object instantiateWithArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Optional<Constructor> argsConstructor = dependencyReflectionRepresentation.getArgsConstructor();
        final Object[] objects = dependentParameters.getConstructorInjectedInstances();
        if (argsConstructor.isPresent()) {
            final Object o = argsConstructor.get().newInstance(objects);
            return o;
        }
        return null;
    }

    private void injectFields(Object o) throws IllegalAccessException {
        if (dependentParameters.getFieldInjectedInstances().length != 0) {
            dependencyReflectionRepresentation.injectFields(o, dependentParameters.getFieldInjectedInstances());
        }
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

    @Override
    public boolean hasFieldInjection() {
        return dependencyReflectionRepresentation.hasInjectedFields();

    }

    @Override
    public boolean hasNoArgsConstructor(Constructor<?>[] declaredConstructors) {
        return dependencyReflectionRepresentation.getNoArgsConstructor().isPresent();

    }

    private Object instantiateWithNoArgsConstructor() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Optional<Constructor> noArgsConstructor = dependencyReflectionRepresentation.getNoArgsConstructor();
        if (noArgsConstructor.isPresent()) {
            this.dependencyInstance = noArgsConstructor.get().newInstance(null);
        }
        return dependencyInstance;
    }

    boolean instantiateDependentParameters() {
        if (dependentParameters != null) {
            return true;
        }
        instantiateParameters();
        return true;
    }

    private void instantiateParameters() {
        dependentParameters = getDependentParamsFromFieldsAndConstructor();
    }

    private DependentParams getDependentParamsFromFieldsAndConstructor() {
        return new DependentParams()
                .getDependentParamsForClass(dependencyReflectionRepresentation, dependencyContextService);
    }

    public Object[] getFieldDependentInstances() {
        return dependentParameters.getFieldInjectedInstances();
    }

    public Object[] getConstructorDependentInstances() {
        return dependentParameters.getConstructorInjectedInstances();
    }
}
