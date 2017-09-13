package tooling;

import model.DependencyContext;
import model.ReflectionTool;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aki on 12.9.2017.
 */
public class MiniDi {
    public static DependencyContext startApplication(Class<?> startClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ReflectionTool reflectionTool = new ReflectionTool();
        DependencyContext context = new DependencyContext();
        DependencyContextService dependencyContextService = new DependencyContextService(reflectionTool,context);
        ClassPathResourceService classPathResourceService = new ClassPathResourceService(dependencyContextService, reflectionTool);
        classPathResourceService.createDependenciesFromClassPath(startClass);
        return context;
    }
}
