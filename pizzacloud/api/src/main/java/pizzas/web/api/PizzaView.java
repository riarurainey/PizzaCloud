package pizzas.web.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import pizzas.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PizzaView {
    private Long id;
    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();

    public PizzaView(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }


}
