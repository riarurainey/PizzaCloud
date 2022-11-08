package pizzas.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pizzas.Ingredient;
import pizzas.IngredientRef;
import pizzas.Pizza;
import pizzas.data.IngredientRepository;
import pizzas.data.OrderRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DesignPizzaController.class)
public class DesignPizzaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private List<Ingredient> ingredients;

    private Pizza test;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        ingredients = Arrays.asList(
                new Ingredient("CLS", "Classic Crust", Ingredient.Type.WRAP),
                new Ingredient("THN", "Thin Crust", Ingredient.Type.WRAP),
                new Ingredient("CHRZ", "Chorizo", Ingredient.Type.PROTEIN),
                new Ingredient("PEP", "Pepperoni", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("ON", "Onion", Ingredient.Type.VEGGIES),
                new Ingredient("BLG", "Bulgarian pepper", Ingredient.Type.VEGGIES),
                new Ingredient("OLV", "Olive", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("MOZ", "Mozzarella", Ingredient.Type.CHEESE),
                new Ingredient("PAR", "Parmesan", Ingredient.Type.CHEESE),
                new Ingredient("DRB", "Danish blue", Ingredient.Type.CHEESE),
                new Ingredient("FET", "Feta", Ingredient.Type.CHEESE),
                new Ingredient("RNC", "Ranch", Ingredient.Type.CHEESE),
                new Ingredient("TMT", "Tomato Sauce", Ingredient.Type.SAUCE),
                new Ingredient("MSTR", "Mustard sauce", Ingredient.Type.SAUCE));

        when(ingredientRepository.findAll())
                .thenReturn(ingredients);

        when(ingredientRepository.findById("CLS"))
                .thenReturn(Optional.of(new Ingredient("CLS", "Classic Crust", Ingredient.Type.WRAP)));
        when(ingredientRepository.findById("PEP"))
                .thenReturn(Optional.of(new Ingredient("PEP", "Pepperoni", Ingredient.Type.PROTEIN)));
        when(ingredientRepository.findById("CHED"))
                .thenReturn(Optional.of(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE)));
        test = new Pizza();
        test.setName("Test Pizza");
        test.setIngredients(
                Arrays.asList(new IngredientRef("CLS"), new IngredientRef("PEP"), new IngredientRef("CHED"))
        );

    }

    @Test
    public void testShowDesignForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 8)))
                .andExpect(model().attribute("cheese", ingredients.subList(8, 14)))
                .andExpect(model().attribute("sauce", ingredients.subList(14, 16)));


    }

    @Test
    public void processPizza() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/design")
                        .content("name=Test+Pizza&ingredients=CLS,PEP,CHED")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));

    }
}


