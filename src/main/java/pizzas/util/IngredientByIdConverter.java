package pizzas.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pizzas.dao.model.Ingredient;
import pizzas.dao.repository.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepository;

    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
