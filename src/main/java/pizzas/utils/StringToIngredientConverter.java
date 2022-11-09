package pizzas.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pizzas.Ingredient;
import pizzas.IngredientUDT;
import pizzas.data.IngredientRepository;

import java.util.Optional;

@Component
public class StringToIngredientConverter implements Converter<String, IngredientUDT> {

    private final IngredientRepository ingredientRepository;

    public StringToIngredientConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientUDT convert(String id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isEmpty()) {
            return null;
        }

        return ingredient.map(i -> new IngredientUDT(i.getName(), i.getType())).get();
    }

}