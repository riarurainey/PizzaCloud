package pizzas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pizzas.data.IngredientRepository;
import pizzas.model.Ingredient;
import pizzas.model.Ingredient.Type;


@SpringBootApplication
public class PizzaCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repository) {
        return args -> {
            repository.save(new Ingredient("CLS", "Classic Crust", Type.WRAP));
            repository.save(new Ingredient("THN", "Thin Crust", Type.WRAP));
            repository.save(new Ingredient("CHRZ", "Chorizo", Type.PROTEIN));
            repository.save(new Ingredient("PEP", "Pepperoni", Type.PROTEIN));
            repository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
            repository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
            repository.save(new Ingredient("MOZ", "Mozzarella", Type.CHEESE));
            repository.save(new Ingredient("ON", "Onion", Type.VEGGIES));
            repository.save(new Ingredient("TMT", "Tomato Sauce", Type.SAUCE));
            repository.save(new Ingredient("BLG", "Bulgarian pepper", Type.VEGGIES));
            repository.save(new Ingredient("OLV", "Olive", Type.VEGGIES));
            repository.save(new Ingredient("PAR", "Parmesan", Type.CHEESE));
            repository.save(new Ingredient("DRB", "Danish blue", Type.CHEESE));
            repository.save(new Ingredient("FET", "Feta", Type.CHEESE));
            repository.save(new Ingredient("RNC", "Ranch", Type.CHEESE));
        };
    }

}
