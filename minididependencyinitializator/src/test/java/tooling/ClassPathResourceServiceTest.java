package tooling;

import model.ClassPathResource;
import model.Dependency;
import model.ReflectionTool;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Created by Aki on 12.9.2017.
 */
public class ClassPathResourceServiceTest {

    private DependencyContextService dependencyContextService;
    private ReflectionTool reflectionTool;
    private ClassPathResourceService classPathResourceService;

    @Before
    public void setUp() throws Exception {
        dependencyContextService = mock(DependencyContextService.class);
        reflectionTool = mock(ReflectionTool.class);
        this.classPathResourceService = new ClassPathResourceService(dependencyContextService, reflectionTool);
    }

    @Test
    public void createDependenciesFromClassPath() throws Exception {
        when(reflectionTool.findClassPathResources(DummyTestClassWithDependency.class)).thenReturn(Collections.singletonList(new ClassPathResource(ClassPathResource.class)));
        when(dependencyContextService.createDependenciesFromResource(ClassPathResource.class)).thenReturn(new Dependency(Dependency.class, dependencyContextService, reflectionTool));
        classPathResourceService.createDependenciesFromClassPath(DummyTestClassWithDependency.class);
        verify(reflectionTool, times(1)).findClassPathResources(any());
        verify(dependencyContextService, times(1)).createDependenciesFromResource(ClassPathResource.class);
    }
}