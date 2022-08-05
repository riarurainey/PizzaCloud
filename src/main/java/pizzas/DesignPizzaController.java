package pizzas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pizzas.Ingredient.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
public class DesignPizzaController {
    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("CLS", "Classic Crust", Type.WRAP),
                new Ingredient("THN", "Thin Crust", Type.WRAP),
                new Ingredient("CHRZ", "Chorizo", Type.PROTEIN),
                new Ingredient("PEP", "Pepperoni", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("MOZ", "Mozzarella", Type.CHEESE),
                new Ingredient("ON", "Onion", Type.VEGGIES),
                new Ingredient("TMT", "Tomato Sauce", Type.SAUCE),
                new Ingredient("BLG", "Bulgarian pepper", Type.VEGGIES),
                new Ingredient("OLV", "Olive", Type.VEGGIES),
                new Ingredient("PAR", "Parmesan", Type.CHEESE),
                new Ingredient("DRB", "Danish blue", Type.CHEESE),
                new Ingredient("FET", "Feta", Type.CHEESE),
                new Ingredient("RNC", "Ranch", Type.CHEESE));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

    }

    @ModelAttribute(name = "pizzaOrder")
    public PizzaOrder order() {
        return new PizzaOrder();
    }

    @ModelAttribute(name = "pizza")
    public Pizza pizza() {
        return new Pizza();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
