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

    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;

    }

    @GetMapping(params = "recent")
    public Flux<Pizza> recentPizza() {
        return pizzaRepository.findAll().take(12);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pizza> postPizza(@RequestBody Mono<Pizza> pizzaMono) {
       return pizzaRepository.saveAll(pizzaMono).next();

    }

    @GetMapping("/{id}")
    public Mono<Pizza> getPizzaById(@PathVariable String id) {
        return pizzaRepository.findById(id);
    }

}
