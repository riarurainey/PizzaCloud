package pizzas;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pizzas.Ingredient;
import pizzas.IngredientService;

@RestController
@RequestMapping("/admin/ingredients")
public class ManageIngredientsController {

    private final IngredientService ingredientService;

    public ManageIngredientsController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public String ingredientsAdmin(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        return "ingredientsAdmin";
    }

    @PostMapping
    public String addIngredient(Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return "redirect:/admin/ingredients";
    }

}
