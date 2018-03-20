package tooling;

import model.ClassPathResourceService;
import model.components.DependencyComponentFactory;
import model.DependencyContext;
import model.DependencyFactory;
import model.tree.CycleChecker;
import model.tree.CycleCheckingDependencyFactory;
import model.tree.PriorityCheckingDependencyFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
public class MiniDi {
    public static DependencyContext startApplication(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionTool reflectionTool = new ReflectionTool();
        DependencyContext context = new DependencyContext();
        ReflectionToolSet reflectionToolSet = new ReflectionUtils();
        DependencyComponentFactory dependencyComponentFactory = new PriorityCheckingDependencyFactory(new CycleCheckingDependencyFactory(new DependencyFactory(reflectionToolSet),
                new CycleChecker(reflectionToolSet), reflectionToolSet), reflectionToolSet);
        ClassPathResourceService classPathResourceService = new ClassPathResourceService(dependencyComponentFactory, reflectionTool, context, reflectionToolSet);
        classPathResourceService.createDependenciesFromClassPath(startClass);
        return context;
    }
}
