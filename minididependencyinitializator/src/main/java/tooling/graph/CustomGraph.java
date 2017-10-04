package tooling.graph;

import model.DependencyReflectionRepresentation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aki on 4.10.2017.
 */
class CustomGraph extends GraphComponent {
    private final Class<?> dependencyClass;
    private final DependencyReflectionRepresentation dependencyReflectionRepresentation;

    CustomGraph(Class<?> dependencyClass, DependencyReflectionRepresentation dependencyReflectionRepresentation) {
        super(new HashMap<>());
        this.dependencyClass = dependencyClass;
        this.dependencyReflectionRepresentation = dependencyReflectionRepresentation;
    }

    CustomGraph initGraph() {
        List<GraphComponent> edges = new GraphEdge(dependencyClass, dependencyReflectionRepresentation)
            .findEdges();
        addComponents(this, edges);
        return this;
    }

    void checkForCycles() {
    }
}
