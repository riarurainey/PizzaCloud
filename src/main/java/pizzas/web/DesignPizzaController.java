package pizzas.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pizzas.Ingredient;
import pizzas.Ingredient.Type;
import pizzas.Pizza;
import pizzas.PizzaOrder;
import pizzas.User;
import pizzas.data.IngredientRepository;
import pizzas.data.PizzaRepository;
import pizzas.data.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
@AllArgsConstructor

public class DesignPizzaController {
    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;
    private final UserRepository userRepository;

    @ModelAttribute(name = "pizzaOrder")
    public PizzaOrder order() {
        return new PizzaOrder();
    }

    @ModelAttribute(name = "pizza")
    public Pizza pizza() {
        return new Pizza();
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        return user;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processPizza(@Valid Pizza pizza, Errors errors, @ModelAttribute PizzaOrder pizzaOrder) {
        if (errors.hasErrors()) {
            return "design";
        }
        Pizza saved = pizzaRepository.save(pizza);
        pizzaOrder.addPizza(saved);
        return "redirect:/orders/current";
    }


    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());

    }
}