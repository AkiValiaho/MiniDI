import annotations.Autowired;
import annotations.Component;

/**
 * Created by Aki on 13.9.2017.
 */
@Component
public class ClientCodeDependency {
    private final ClientDependency clientDependency;

    @Autowired
    public ClientCodeDependency(ClientDependency clientDependency) {
        this.clientDependency = clientDependency;
    }
}
