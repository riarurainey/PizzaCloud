package pizzas;

import lombok.Data;
import pizzas.Ingredient;

import java.util.List;

@Data
public class Pizza {
    private String name;
    private List<Ingredient> ingredients;
}
