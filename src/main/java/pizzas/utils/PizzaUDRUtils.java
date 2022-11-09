package pizzas.utils;

import pizzas.Ingredient;
import pizzas.IngredientUDT;
import pizzas.Pizza;
import pizzas.PizzaUDT;

import java.util.List;
import java.util.stream.Collectors;

public class PizzaUDRUtils {
    public static PizzaUDT toPizzaUDT(Pizza pizza) {
        return new PizzaUDT(pizza.getName(), pizza.getIngredients());

    }

    public static List<IngredientUDT> toIngredientUDTs(List<Ingredient> ingredients) {
        return ingredients.stream().map(PizzaUDRUtils::toIngredientUDT)
                .collect(Collectors.toList());

    }

    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }

}