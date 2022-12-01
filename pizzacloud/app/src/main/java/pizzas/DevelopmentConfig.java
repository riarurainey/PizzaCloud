package pizzas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repository,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder,
                                        PizzaRepository pizzaRepository,
                                        OrderRepository orderRepository) {
        return args -> {

            Ingredient crustClassic = new Ingredient("CLS", "Classic Crust", Ingredient.Type.WRAP);
            Ingredient crustThin = new Ingredient("THN", "Thin Crust", Ingredient.Type.WRAP);
            Ingredient chorizo = new Ingredient("CHRZ", "Chorizo", Ingredient.Type.PROTEIN);
            Ingredient pepperoni = new Ingredient("PEP", "Pepperoni", Ingredient.Type.PROTEIN);
            Ingredient dicedTomatoes = new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
            Ingredient onion = new Ingredient("ON", "Onion", Ingredient.Type.VEGGIES);
            Ingredient bulgarPepper = new Ingredient("BLG", "Bulgarian pepper", Ingredient.Type.VEGGIES);
            Ingredient olive = new Ingredient("OLV", "Olive", Ingredient.Type.VEGGIES);
            Ingredient cheddar = new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
            Ingredient mozzarella = new Ingredient("MOZ", "Mozzarella", Ingredient.Type.CHEESE);
            Ingredient parmesan = new Ingredient("PAR", "Parmesan", Ingredient.Type.CHEESE);
            Ingredient danishBlue = new Ingredient("DRB", "Danish blue", Ingredient.Type.CHEESE);
            Ingredient feta = new Ingredient("FET", "Feta", Ingredient.Type.CHEESE);
            Ingredient ranch = new Ingredient("RNC", "Ranch", Ingredient.Type.CHEESE);
            Ingredient tomatoSauce = new Ingredient("TMT", "Tomato Sauce", Ingredient.Type.SAUCE);
            Ingredient mustardSauce = new Ingredient("MSTR", "Mustard sauce", Ingredient.Type.SAUCE);

            repository.save(crustClassic);
            repository.save(crustThin);
            repository.save(chorizo);
            repository.save(pepperoni);
            repository.save(dicedTomatoes);
            repository.save(onion);
            repository.save(bulgarPepper);
            repository.save(olive);
            repository.save(cheddar);
            repository.save(mozzarella);
            repository.save(parmesan);
            repository.save(danishBlue);
            repository.save(feta);
            repository.save(ranch);
            repository.save(tomatoSauce);
            repository.save(mustardSauce);

            User user = new User("pizza_user", passwordEncoder.encode("pizza_pass"),
                    "Pizza User", "Pizza Street", "Pizza City", "PZ",
                    "11111", "111-111-1111");
            userRepository.save(user);

            Pizza pizza1 = new Pizza();
            pizza1.setName("4 Cheese");
            pizza1.setIngredients(List.of(crustThin, cheddar, mozzarella, feta, danishBlue));
            pizzaRepository.save(pizza1);


            Pizza pizza2 = new Pizza();
            pizza2.setName("Pepperoni");
            pizza2.setIngredients(List.of(crustClassic, pepperoni, mozzarella, tomatoSauce));
            pizzaRepository.save(pizza2);


            Pizza pizza3 = new Pizza();
            pizza3.setName("Margarita");
            pizza3.setIngredients(List.of(crustClassic, dicedTomatoes, mozzarella, tomatoSauce));
            pizzaRepository.save(pizza3);

            PizzaOrder pizzaOrder = new PizzaOrder();
            pizzaOrder.setPizzas(new ArrayList<>());

            pizzaOrder.setUser(user);
            pizzaOrder.setDeliveryName(user.getUsername());
            pizzaOrder.setDeliveryStreet(user.getStreet());
            pizzaOrder.setDeliveryCity(user.getCity());
            pizzaOrder.setDeliveryState(user.getState());
            pizzaOrder.setDeliveryZip(user.getZip());
            pizzaOrder.setCcNumber("4878667725907502");
            pizzaOrder.setCcExpiration("11/24");
            pizzaOrder.setCcCVV("111");
            pizzaOrder.setPlacedAt(Date.from(Instant.now()));
            orderRepository.save(pizzaOrder);


        };
    }
}