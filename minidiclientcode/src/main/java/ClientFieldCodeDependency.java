import annotations.Autowired;
import annotations.Component;

@Component
public class ClientFieldCodeDependency {
    @Autowired
    ClientFieldDependency clientFieldDependency;
}
