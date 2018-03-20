package model;

import model.components.DependencyComponentFactory;
import model.components.DependencyContextComponent;
import tooling.ReflectionTool;
import tooling.ReflectionToolSet;

/**
 * Created by akivv on 5.9.2017.
 */
public class DependencyContextService extends DependencyContextComponent {

    public DependencyContextService(ReflectionTool reflectionTool, DependencyContext dependencyContext, DependencyComponentFactory dependencyComponentFactory, ReflectionToolSet reflectionToolSet) {
        super(dependencyComponentFactory, reflectionTool, dependencyContext, reflectionToolSet);
    }


}
