package tooling;

import model.DependencyContext;
import model.DependencyFactory;
import tooling.tree.CycleChecker;
import tooling.tree.CycleCheckingDependencyFactory;
import tooling.tree.PriorityChecker;
import tooling.tree.PriorityCheckingDependencyFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
public class MiniDi {
    public static DependencyContext startApplication(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionTool reflectionTool = new ReflectionTool();
        DependencyContext context = new DependencyContext();
        DependencyComponentFactory dependencyComponentFactory = new PriorityCheckingDependencyFactory(new CycleCheckingDependencyFactory(new DependencyFactory(),
                new CycleChecker()), new PriorityChecker());
        ClassPathResourceService classPathResourceService = new ClassPathResourceService(dependencyComponentFactory, reflectionTool, context);
        classPathResourceService.createDependenciesFromClassPath(startClass);
        return context;
    }
}
