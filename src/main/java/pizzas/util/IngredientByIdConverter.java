package pizzas.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pizzas.models.Ingredient;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put("CLS", new Ingredient("CLS", "Classic Crust", Ingredient.Type.WRAP));
        ingredientMap.put("THN", new Ingredient("THN", "Thin Crust", Ingredient.Type.WRAP));
        ingredientMap.put("CHRZ", new Ingredient("CHRZ", "Chorizo", Ingredient.Type.PROTEIN));
        ingredientMap.put("PEP", new Ingredient("PEP", "Pepperoni", Ingredient.Type.PROTEIN));
        ingredientMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientMap.put("MOZ", new Ingredient("MOZ", "Mozzarella", Ingredient.Type.CHEESE));
        ingredientMap.put("ON", new Ingredient("ON", "Onion", Ingredient.Type.VEGGIES));
        ingredientMap.put("TMT", new Ingredient("TMT", "Tomato Sauce", Ingredient.Type.SAUCE));
        ingredientMap.put("BLG", new Ingredient("BLG", "Bulgarian pepper", Ingredient.Type.VEGGIES));
        ingredientMap.put("OLV", new Ingredient("OLV", "Olive", Ingredient.Type.VEGGIES));
        ingredientMap.put("PAR", new Ingredient("PAR", "Parmesan", Ingredient.Type.CHEESE));
        ingredientMap.put("DRB", new Ingredient("DRB", "Danish blue", Ingredient.Type.CHEESE));
        ingredientMap.put("FET", new Ingredient("FET", "Feta", Ingredient.Type.CHEESE));
        ingredientMap.put("RNC", new Ingredient("RNC", "Ranch", Ingredient.Type.CHEESE));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
