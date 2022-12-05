package pizzas;

import lombok.Data;

import java.util.List;

@Data
public class Pizza {
    private final String name;
    private List<String> ingredients;
}
