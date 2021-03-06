# MiniDI
### Status
[![Build Status](https://travis-ci.org/AkiValiaho/MiniDI.svg)](https://travis-ci.org/AkiValiaho/MiniDI)

Small scale dependency injection framework

Uses annotation-based dependency resolving. Detects cycles in your
dependency declarations.

Latest news:
    
    DependsOn functionality added
      
     @DependsOn-annotation can be used to influence dependency instantiation
     order.
        
        eg.
        @Component
        @DependsOn(MagicDependency.class)
        public class Dependency {
            private String exampleString;
        }   
    
    
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
 
 PostConstruct functionality
        
        A method taking no parameters of a class instantiated can be forced to be called
        after class instantiation. Use the @PostConstruct-annotation
        
        eg.
        @Component
        public class Dependency {
            private String exampleString;
            @PostConstruct
            public void  callThis() {
                this.exampleString = "hello";
            }
            
            @Autowired
            public Dependency(MagicDependency magicDependency) {
            }
        }    
 
Application startup:

    To bootstrap your application start it with the following template:
        MiniDi.startApplication(<-Your application class->);
    This template also returns a DependencyContext you can use to query for dependencies.

    
