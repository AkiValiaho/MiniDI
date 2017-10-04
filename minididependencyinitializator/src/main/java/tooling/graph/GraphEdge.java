package tooling.graph;

import model.DependencyReflectionRepresentation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aki on 4.10.2017.
 */
class GraphEdge extends GraphComponent{
    private final Class<?> dependencyClass;
    private final DependencyReflectionRepresentation dependencyReflectionRepresentation;

    GraphEdge(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) {
        super(new HashMap<>());
        this.dependencyClass = dependencyClass;
        this.dependencyReflectionRepresentation = dependencyReflectionRepresentation;
    }

    List<GraphComponent> findEdges() {
        return null;
    }
}
