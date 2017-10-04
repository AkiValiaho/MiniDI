package tooling;

import model.*;
import tooling.graph.CycleChecker;
import tooling.graph.CycleCheckingDependencyFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
public class MiniDi {
    public static DependencyContext startApplication(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionTool reflectionTool = new ReflectionTool();
        DependencyContext context = new DependencyContext();
        DependencyFactoryComponent dependencyFactory = new CycleCheckingDependencyFactory(new DependencyFactory(), new CycleChecker());
        ClassPathResourceService classPathResourceService = new ClassPathResourceService(dependencyFactory, reflectionTool, context);
        classPathResourceService.createDependenciesFromClassPath(startClass);
        return context;
    }
}
