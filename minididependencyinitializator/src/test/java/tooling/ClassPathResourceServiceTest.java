package tooling;

import model.DependencyContext;
import model.DependencyFactoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 27.9.2017.
 */
public class ClassPathResourceServiceTest {
    private ClassPathResourceService classPathResourceservice;
    private DependencyContext dependencyContext;

    @Before
    public void setUp() throws Exception {
        final ReflectionTool reflectionTool = new ReflectionTool();
        dependencyContext = new DependencyContext();
        DependencyFactoryImpl dependencyFactoryImpl = new DependencyFactoryImpl();
        this.classPathResourceservice = new ClassPathResourceService(dependencyFactoryImpl, reflectionTool, dependencyContext);
    }

    @Test
    public void createDependenciesFromClassPath() throws Exception {
        this.classPathResourceservice.createDependenciesFromClassPath(DummyTestClassWithDependency.class);
        //Dependency Context should contain five dependencies now
        assertTrue(dependencyContext.numberOfDependencies() == 7);
    }
}