package pizzas.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzas.Ingredient;
import pizzas.IngredientRepository;

import java.util.Optional;


@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> allIngredient() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Ingredient> getIngredientById(@PathVariable("id") String id) {
        return ingredientRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);

    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public Ingredient updateIngredients(@PathVariable("id") String id,
                                        @RequestBody Ingredient ingredient) {
        ingredient.setId(id);
        return ingredientRepository.save(ingredient);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") String id) {
        ingredientRepository.deleteById(id);

    }

}
