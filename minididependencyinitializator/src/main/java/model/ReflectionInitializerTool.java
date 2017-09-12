package model;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by akivv on 5.9.2017.
 */
public class ReflectionInitializerTool {

    public Object initialize(Dependency dependency) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (dependency.isLeafParameter()) {
            //TODO Simplify this logic a bit (move noargs constructor search into the dependency model object
            return instantiateWithNoArgsConstructor(dependency);
        }
        //Not a leaf parameter
        if (dependency.instantiateDependentParameters()) {
            //We can now instantiate from the constructor
            return instantiateFromArgsConstructor(dependency);
        } else {
        }
        return null;
    }

    private Object instantiateFromArgsConstructor(Dependency dependencyClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
 return        dependencyClass.instantiateWithArgsConstructor();
    }

    private Object instantiateWithNoArgsConstructor(Dependency dependencyClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return dependencyClass.instantiateWithNoArgsConstructor();
    }
}
