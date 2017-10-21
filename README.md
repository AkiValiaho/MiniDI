# MiniDI
### Status
[![Build Status](https://travis-ci.org/AkiValiaho/MiniDI.svg)](https://travis-ci.org/AkiValiaho/MiniDI)

Small scale dependency injection framework

Uses annotation-based dependency resolving. Detects cycles in your
dependency declarations (this is done with a fairly naive strategy: just construct
a tree recursively from each dependency and check if it contains any cycles back to the root node, working on a 
more performant version at the momentt)

Maven dependency:

    **To use MiniDI-framework add the following dependency to your pom.xml**
      
     <dependencies>
        <dependency>
            <groupId>com.akivaliaho</groupId>
               <artifactId>minididependencyinitializator</artifactId>
               <version>1.0</version>
        </dependency>
    </dependencies>

Annotations:

Class-annotation:
    
    @Component: Component is an entity that is suitable for dependency injection. Use it on top of your class to mark
    that you want the class to be injected.

Constructor-annotation:
    
    @Autowired: Marks a constructor as injectable, MiniDI searches for
    all the recursive dependencies marked with @Component-annotation and injects them through
    this annotated constructor.
    
    eg.
    @Component
    public class Dependency {
        @Autowired
        public Dependency(MagicDependency magicDependency) {
        }
    }
    
Field-annotation
    
    You can also inject fields without the constructor annotation using the @Autowired-annotation on the field
    of your choice.
    eg.
    
    @Component
    public class Dependency {
        @Autowired
        private MagicDependency magicDependency;
     }
    
Hybrid-approach
    
    You can also mix both constructor and field injections as shown below:
    
    @Component
    public class Dependency {
        @Autowired
        private FieldDependency fieldDependency;
        
        private ConstructorDependency constructorDependency;
        
        @Autowired
        public class Dependency(ConstructorDependency constructorDependency) {
            this.constructorDependency = constructorDependency;
        }
    }
 
Application startup:

    To bootstrap your application start it with the following template:
        MiniDi.startApplication(<-Your application class->);
    This template also returns a DependencyContext you can use to query for dependencies.
    
    
Features currently under development:
    
    @DependsOn-annotation to control the flow of dependency resolution. 21.10.2017 -> stubs written for the decorator
    @PostConstruct-annotation to call a certain method after dependency is instantiated
        this  could be a lazy call after all the instances are instantiated or a call that is done
        immediately after the annotated instance is done (enum to control behaviour)

    