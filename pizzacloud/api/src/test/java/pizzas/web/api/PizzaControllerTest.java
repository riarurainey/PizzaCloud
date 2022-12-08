package pizzas.web.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pizzas.Ingredient;
import pizzas.Pizza;
import pizzas.PizzaRepository;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PizzaControllerTest {

    @Test
    public void shouldSavePizza() {
        PizzaRepository pizzaRepository = Mockito.mock(PizzaRepository.class);
        Mono<Pizza> unsavedPizzaMono = Mono.just(testPizza(null));
        Pizza savedPizza = testPizza(null);
        Mono<Pizza> savedPizzaMono = Mono.just(savedPizza);

        when(pizzaRepository.save(any())).thenReturn(savedPizzaMono);

        WebTestClient testClient = WebTestClient.bindToController(
                new PizzaController(pizzaRepository, null)).build();

        testClient.post()
                .uri("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedPizzaMono, Pizza.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Pizza.class)
                .isEqualTo(savedPizza);


    }

    private Pizza testPizza(Long number) {
        Pizza pizza = new Pizza();
        pizza.setId(number);
        pizza.setName("Pizza " + number);
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredientA = new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP);
        Ingredient ingredientB = new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN);
        ingredients.add(
                ingredientA);
        ingredients.add(ingredientB);
        pizza.addIngredient(ingredientA);
        pizza.addIngredient(ingredientB);
        return pizza;

    }
}
