# MiniDI
Small scale dependency injection framework

Uses annotation-based injection dependency resolving.

Annotations:

Class-annotation:
    
    @Component: Component is an entity that is suitable for dependency injection. Use it on top of your class to mark
    that you want the class to be injected.

Constructor-annotation:
    
    @Autowired: Marks a constructor as injectable, MiniDI searches for
    all the recursive dependencies marked with @Component-annotation and injects them through
    this annotated constructor.
    
    
Application startup:

    To bootstrap your application start it with the following template:
        MiniDi.startApplication(<-Your application class->);
    This template also returns a DependencyContext you can use to query for dependencies.