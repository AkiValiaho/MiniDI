package tooling;

import model.DependencyContext;
import model.DependencyFactory;
import model.ReflectionUtils;
import tooling.tree.CycleChecker;
import tooling.tree.CycleCheckingDependencyFactory;
import tooling.tree.PriorityCheckingDependencyFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
public class MiniDi {
    public static DependencyContext startApplication(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionTool reflectionTool = new ReflectionTool();
        DependencyContext context = new DependencyContext();
        ReflectionUtils reflectionUtils = new ReflectionUtils();
        DependencyComponentFactory dependencyComponentFactory = new PriorityCheckingDependencyFactory(new CycleCheckingDependencyFactory(new DependencyFactory(reflectionUtils),
                new CycleChecker(reflectionUtils), reflectionUtils), reflectionUtils);
        ClassPathResourceService classPathResourceService = new ClassPathResourceService(dependencyComponentFactory, reflectionTool, context);
        classPathResourceService.createDependenciesFromClassPath(startClass);
        return context;
    }
}
