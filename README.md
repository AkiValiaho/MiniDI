# MiniDI
Small scale dependency injection framework

Uses annotation-based injection dependency resolving.

Maven dependency:

    **To use MiniDI-framework add the following dependency to your pom.xml**
      
     <dependencies>
        <dependency>
            <groupId>com.akivaliaho</groupId>
               <artifactId>minididependencyinitializator</artifactId>
               <version>1.0-SNAPSHOT</version>
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