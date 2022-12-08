package pizzas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pizzas.Ingredient.Type;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repository,
                                        PizzaRepository pizzaRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Ingredient crustClassic = saveAnIngredient("CLS", "Classic Crust", Type.WRAP);
                Ingredient crustThin = saveAnIngredient("THN", "Thin Crust", Type.WRAP);
                Ingredient chorizo = saveAnIngredient("CHRZ", "Chorizo", Type.PROTEIN);
                Ingredient pepperoni = saveAnIngredient("PEP", "Pepperoni", Type.PROTEIN);
                Ingredient dicedTomatoes = saveAnIngredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
                Ingredient onion = saveAnIngredient("ON", "Onion", Type.VEGGIES);
                Ingredient bulgarPepper = saveAnIngredient("BLG", "Bulgarian pepper", Type.VEGGIES);
                Ingredient olive = saveAnIngredient("OLV", "Olive", Type.VEGGIES);
                Ingredient cheddar = saveAnIngredient("CHED", "Cheddar", Type.CHEESE);
                Ingredient mozzarella = saveAnIngredient("MOZ", "Mozzarella", Type.CHEESE);
                Ingredient parmesan = saveAnIngredient("PAR", "Parmesan", Type.CHEESE);
                Ingredient danishBlue = saveAnIngredient("DRB", "Danish blue", Type.CHEESE);
                Ingredient feta = saveAnIngredient("FET", "Feta", Type.CHEESE);
                Ingredient ranch = saveAnIngredient("RNC", "Ranch", Type.CHEESE);
                Ingredient tomatoSauce = saveAnIngredient("TMT", "Tomato Sauce", Type.SAUCE);
                Ingredient mustardSauce = saveAnIngredient("MSTR", "Mustard sauce", Type.SAUCE);

                Pizza pizza1 = new Pizza();
                pizza1.setName("4 Cheese");

                pizza1.addIngredient(crustThin);
                pizza1.addIngredient(cheddar);
                pizza1.addIngredient(mozzarella);
                pizza1.addIngredient(feta);
                pizza1.addIngredient(danishBlue);
                pizzaRepository.save(pizza1).subscribe();


                Pizza pizza2 = new Pizza();
                pizza2.setName("Pepperoni");
                pizza2.addIngredient(crustClassic);
                pizza2.addIngredient(pepperoni);
                pizza2.addIngredient(mozzarella);
                pizza2.addIngredient(tomatoSauce);
                pizzaRepository.save(pizza2).subscribe();


                Pizza pizza3 = new Pizza();
                pizza3.setName("Margarita");
                pizza3.addIngredient(crustClassic);
                pizza3.addIngredient(dicedTomatoes);
                pizza3.addIngredient(mozzarella);
                pizza3.addIngredient(tomatoSauce);
                pizzaRepository.save(pizza3).subscribe();
            }

            private Ingredient saveAnIngredient(String id, String name, Ingredient.Type type) {
                Ingredient ingredient = new Ingredient(id, name, type);
                repository.save(ingredient).subscribe();
                return ingredient;
            }
        };
    }
}