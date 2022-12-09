package pizzas.web.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzas.Ingredient;

import pizzas.IngredientRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;


@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Flux<Ingredient> allIngredient() {

        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Ingredient> getIngredientById(@PathVariable("id") String id) {
        return ingredientRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Ingredient>> postIngredient(@RequestBody Mono<Ingredient> ingredient) {
        return ingredient
                .flatMap(ingredientRepository::save)
                .map(i -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create("http://localhost:8080/ingredients" + i.getId()));
                    return new ResponseEntity<>(i, headers, HttpStatus.CREATED);
                });
    }

    @PutMapping("/{id}")
    public void updateIngredients(@PathVariable("id") String id,
                                  @RequestBody Ingredient ingredient) {
        if (!ingredient.getId().equals(id)) {
            throw new IllegalStateException("Given ingredient's ID doesn't match yhe ID on the path");
        }

        ingredientRepository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable String id) {
        ingredientRepository.findById(id)
                .doOnNext(ingredientRepository::delete)
                .subscribe();

    }

}
