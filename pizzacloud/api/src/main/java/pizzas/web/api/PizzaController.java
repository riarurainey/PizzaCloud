package pizzas.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzas.Ingredient;
import pizzas.IngredientRepository;
import pizzas.Pizza;
import pizzas.PizzaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/pizzas", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class PizzaController {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;

    public PizzaController(PizzaRepository pizzaRepository, IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping(params = "recent")
    public Flux<PizzaView> recentPizza() {
        return pizzaRepository
                .findAll()
                .take(12)
                .map(pizza -> {
                    PizzaView pizzaView =
                            new PizzaView(pizza.getId(), pizza.getName());
                    pizza.getIngredientIds()
                            .forEach(ingredientId -> {
                                ingredientRepository.findById(ingredientId)
                                        .subscribe(pizzaView::addIngredient);
                            });
                    return pizzaView;
                });
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pizza> postPizza(@RequestBody PizzaView pizzaView) {
        Pizza pizza = new Pizza(pizzaView.getName());
        for (Ingredient ingredient : pizzaView.getIngredients()) {
            pizza.addIngredient(ingredient);
        }
        return pizzaRepository.save(pizza);
    }

    @GetMapping("/{id}")
    public Mono<Pizza> getPizzaById(@PathVariable("id") Long id) {
        return pizzaRepository.findById(id);
    }

}
