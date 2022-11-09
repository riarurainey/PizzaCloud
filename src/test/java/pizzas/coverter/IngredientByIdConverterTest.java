package pizzas.coverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzas.Ingredient;
import pizzas.data.IngredientRepository;
import pizzas.utils.IngredientByIdConverter;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientByIdConverterTest {

    IngredientByIdConverter convereter;

    @BeforeEach
    public void setup() {
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        when(ingredientRepository.findById("TEST"))
                .thenReturn(Optional.of(new Ingredient("TEST", "TEST INGREDIENT", Ingredient.Type.SAUCE)));
        when(ingredientRepository.findById("EMPTY"))
                .thenReturn(Optional.empty());

        this.convereter = new IngredientByIdConverter(ingredientRepository);

    }

    @Test
    public void shouldReturnValueThenPresent() {
        assertThat(convereter.convert("TEST"))
                .isEqualTo(new Ingredient("TEST", "TEST INGREDIENT", Ingredient.Type.SAUCE));
    }

    @Test
    public void shouldReturnNullThenMissing() {
        assertThat(convereter.convert("EMPTY"))
                .isNull();
    }
}
