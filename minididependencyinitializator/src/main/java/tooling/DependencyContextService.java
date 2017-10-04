package tooling;

import model.DependencyContext;

/**
 * Created by akivv on 5.9.2017.
 */
public class DependencyContextService extends DependencyContextComponent {

    public DependencyContextService(ReflectionTool reflectionTool, DependencyContext dependencyContext, DependencyFactory dependencyFactory) {
        super(dependencyFactory, reflectionTool, dependencyContext);
    }


}
