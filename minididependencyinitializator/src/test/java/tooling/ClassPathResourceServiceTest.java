package tooling;

import model.DependencyContext;
import model.DependencyFactory;
import model.ReflectionToolSet;
import model.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Aki on 27.9.2017.
 */
public class ClassPathResourceServiceTest {
    private ClassPathResourceService classPathResourceservice;
    private DependencyContext dependencyContext;
    private ReflectionToolSet reflectionutils;

    @Before
    public void setUp() throws Exception {
        final ReflectionTool reflectionTool = new ReflectionTool();
        this.reflectionutils = new ReflectionUtils();
        dependencyContext = new DependencyContext();
        DependencyFactory dependencyFactoryImpl = new DependencyFactory(reflectionutils);
        this.classPathResourceservice = new ClassPathResourceService(dependencyFactoryImpl, reflectionTool, dependencyContext, reflectionutils);
    }

    @Test
    public void createDependenciesFromClassPath() throws Exception {
        this.classPathResourceservice.createDependenciesFromClassPath(DummyTestClassWithDependency.class);
        //Dependency Context should contain five dependencies now
        assertTrue(dependencyContext.numberOfDependencies() == 7);
    }
}