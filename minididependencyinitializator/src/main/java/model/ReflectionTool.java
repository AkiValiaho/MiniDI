package model;

import annotations.Autowired;
import annotations.Component;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by akivv on 5.9.2017.
 */
public class ReflectionTool {

    public List<ClassPathResource> findClassPathResources(Class<?> startClass) {
        return getClassesFromClassPathWithAnnotation(startClass).stream()
                .map(ClassPathResource::new)
                .collect(Collectors.toList());
    }

    private List<Class<?>> getClassesFromClassPathWithAnnotation(Class<?> startClass) {
        // Scan classpath to find all @Interest annotated methods
        final Package aPackage = startClass.getPackage();
        return scanClassPathForInterests(getPackageName(aPackage));
    }

    private String getPackageName(Package aPackage) {
        return aPackage == null ? "" : aPackage.getName();
    }

    private List<Class<?>> scanClassPathForInterests(String rootPackage) {
        List<URL> collect = new ArrayList<>(getURLsForPackage(rootPackage));
        Configuration configuration = new ConfigurationBuilder().addUrls(collect)
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new MethodAnnotationsScanner(),
                        new FieldAnnotationsScanner());
        Reflections reflections = new Reflections(configuration);
        Set<Class<?>> classesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class);
        return new ArrayList<>(classesAnnotatedWith);
    }

    private Collection<URL> getURLsForPackage(String string) {
        Collection<URL> forPackage = ClasspathHelper.forPackage(string, null);
        //Add the package ending to the url
        return forPackage.stream()
                .map(msg -> {
                    try {
                        return appendPackageName(string, msg);
                    } catch (MalformedURLException e) {
                        System.out.println(e);
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private URL appendPackageName(String string, URL msg) throws MalformedURLException {
        String changedPackageName = string.replaceAll("\\.", "/");
        String urlAsString = msg.toString();
        return new URL(urlAsString + changedPackageName);
    }

    public Object initialize(Dependency dependency) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return dependency.instantiate();
    }

    boolean hasAutowiredAnnotation(AnnotatedElement reflectionMember) {
        return reflectionMember.isAnnotationPresent(Autowired.class);
    }

    ReflectionRepresentation getReflectionRepresentation(Class<?> dependencyClass) {
        return new ReflectionRepresentation(dependencyClass, this).represent();
    }
}
