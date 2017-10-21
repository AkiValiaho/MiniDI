package tooling;

import model.DependencyContext;

/**
 * Created by akivv on 5.9.2017.
 */
public class DependencyContextService extends DependencyContextComponent {

    public DependencyContextService(ReflectionTool reflectionTool, DependencyContext dependencyContext, DependencyComponentFactory dependencyComponentFactory) {
        super(dependencyComponentFactory, reflectionTool, dependencyContext);
    }


}
