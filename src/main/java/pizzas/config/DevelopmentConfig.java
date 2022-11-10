package pizzas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pizzas.dao.model.Ingredient;
import pizzas.dao.repository.IngredientRepository;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repository) {
        return args -> {
            repository.deleteAll();
            repository.save(new Ingredient("CLS", "Classic Crust", Ingredient.Type.WRAP));
            repository.save(new Ingredient("THN", "Thin Crust", Ingredient.Type.WRAP));
            repository.save(new Ingredient("CHRZ", "Chorizo", Ingredient.Type.PROTEIN));
            repository.save(new Ingredient("PEP", "Pepperoni", Ingredient.Type.PROTEIN));
            repository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            repository.save(new Ingredient("ON", "Onion", Ingredient.Type.VEGGIES));
            repository.save(new Ingredient("BLG", "Bulgarian pepper", Ingredient.Type.VEGGIES));
            repository.save(new Ingredient("OLV", "Olive", Ingredient.Type.VEGGIES));
            repository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("MOZ", "Mozzarella", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("PAR", "Parmesan", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("DRB", "Danish blue", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("FET", "Feta", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("RNC", "Ranch", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("TMT", "Tomato Sauce", Ingredient.Type.SAUCE));
            repository.save(new Ingredient("MSTR", "Mustard sauce", Ingredient.Type.SAUCE));
        };
    }
}
