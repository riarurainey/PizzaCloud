package pizzas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootConfiguration
@ComponentScan
@Slf4j
public class RestExamples {
    public static void main(String[] args) {
        SpringApplication.run(RestExamples.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner fetchIngredients(PizzaCloudClient pizzaCloudClient) {
        return args -> {
            log.info("----------------------- GET -------------------------");
            log.info("GETTING INGREDIENT BY IDE");
            log.info("Ingredient:  " + pizzaCloudClient.getIngredientById("CHED"));
            log.info("GETTING ALL INGREDIENTS");
            List<Ingredient> ingredients = pizzaCloudClient.getAllIngredients();
            log.info("All ingredients:");
            for (Ingredient ingredient : ingredients) {
                log.info("   - " + ingredient);
            }
        };
    }

    @Bean
    public CommandLineRunner putAnIngredient(PizzaCloudClient pizzaCloudClient) {
        return args -> {
            log.info("----------------------- PUT -------------------------");
            Ingredient before = pizzaCloudClient.getIngredientById("MOZ");
            log.info("BEFORE:  " + before);
            pizzaCloudClient.updateIngredient(new Ingredient("MOZ", "Super Mozzarella", Ingredient.Type.CHEESE));
            Ingredient after = pizzaCloudClient.getIngredientById("MOZ");
            log.info("AFTER:  " + after);
        };
    }

    @Bean
    public CommandLineRunner addAnIngredient(PizzaCloudClient pizzaCloudClient) {
        return args -> {
            log.info("----------------------- POST -------------------------");
            Ingredient jalapeno = new Ingredient("JAL", "Jalapeno", Ingredient.Type.VEGGIES);
            Ingredient jalapenoAfter = pizzaCloudClient.createIngredient(jalapeno);
            log.info("AFTER:  " + jalapenoAfter);
        };
    }


    @Bean
    public CommandLineRunner deleteAnIngredient(PizzaCloudClient pizzaCloudClient) {
        return args -> {
            log.info("----------------------- DELETE -------------------------");
            // start by adding a few ingredients so that we can delete them later...
            Ingredient beefFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
            pizzaCloudClient.createIngredient(beefFajita);
            Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
            pizzaCloudClient.createIngredient(shrimp);

            Ingredient before = pizzaCloudClient.getIngredientById("BFFJ");
            log.info("BEFORE:  " + before);
            pizzaCloudClient.deleteIngredient(before);
            Ingredient after = pizzaCloudClient.getIngredientById("BFFJ");
            log.info("AFTER:  " + after);
            before = pizzaCloudClient.getIngredientById("SHMP");
            log.info("BEFORE:  " + before);
            pizzaCloudClient.deleteIngredient(before);
            after = pizzaCloudClient.getIngredientById("SHMP");
            log.info("AFTER:  " + after);
        };
    }
}
