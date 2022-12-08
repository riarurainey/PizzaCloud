package pizzas.web.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pizzas.Ingredient;
import pizzas.Pizza;
import pizzas.PizzaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PizzaControllerTest {
    @Test
    public void shouldReturnRecentPizzas() {
        Pizza[] pizzas = {
                testPizza(1L),
                testPizza(2L),
                testPizza(3L),
                testPizza(4L),
                testPizza(5L),
                testPizza(6L),
                testPizza(7L),
                testPizza(8L),
                testPizza(9L),
                testPizza(10L),
                testPizza(11L),
                testPizza(12L),
                testPizza(13L),
                testPizza(14L),
                testPizza(15L),
                testPizza(16L)};

        Flux<Pizza> pizzaFlux = Flux.just(pizzas);


        PizzaRepository pizzaRepository = Mockito.mock(PizzaRepository.class);
        when(pizzaRepository.findAll()).thenReturn(pizzaFlux);


        WebTestClient testClient = WebTestClient.bindToController(new PizzaController(pizzaRepository)).build();

        testClient.get().uri("/api/pizzas?recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(pizzas[0].getId())
                .jsonPath("$[0].name").isEqualTo("Pizza 1")
                .jsonPath("$[1].id").isEqualTo(pizzas[1].getId())
                .jsonPath("$[1].name").isEqualTo("Pizza 2")
                .jsonPath("$[11].id").isEqualTo(pizzas[11].getId())
                .jsonPath("$[11].name").isEqualTo("Pizza 12")
                .jsonPath("$[12]").doesNotExist();


    }

    @Test
    public void shouldSavePizza() {
        PizzaRepository pizzaRepository = Mockito.mock(PizzaRepository.class);
        Mono<Pizza> unsavedPizzaMono = Mono.just(testPizza(null));
        Pizza savedPizza = testPizza(null);
        Mono<Pizza> savedPizzaMono = Mono.just(savedPizza);

        when(pizzaRepository.save(any())).thenReturn(savedPizzaMono);

        WebTestClient testClient = WebTestClient.bindToController(
                new PizzaController(pizzaRepository)).build();

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
        pizza.setId(number != null ? number.toString() : "TESTID");
        pizza.setName("Pizza " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredientA = new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP);
        Ingredient ingredientB = new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN);
        ingredients.add(
                ingredientA);
        ingredients.add(
                ingredientB);
        pizza.setIngredients(ingredients);

        return pizza;

    }
}
