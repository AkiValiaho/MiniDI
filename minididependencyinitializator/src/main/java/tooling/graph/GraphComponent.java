package tooling.graph;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Aki on 4.10.2017.
 */
abstract class GraphComponent {
    Map<GraphComponent, List<GraphComponent>> relativeComponents;

    GraphComponent(Map<GraphComponent, List<GraphComponent>> relativeComponents) {
        this.relativeComponents = relativeComponents;
    }

    void addComponents(GraphComponent graphComponent, List<GraphComponent> relatives) {
        if (relatives != null && graphComponent != null) {
            relatives
                    .forEach(relative -> addComponent(graphComponent, relative));
        }
    }

    private void addComponent(GraphComponent graphComponent, GraphComponent relative) {
        if (relativeComponents.containsKey(graphComponent)) {
            final List<GraphComponent> graphComponents = relativeComponents.get(graphComponent);
            graphComponents.add(relative);
        } else {
            final ArrayList<GraphComponent> value = new ArrayList<>();
            value.add(relative);
            relativeComponents.put(graphComponent, value);
        }
    }
}
