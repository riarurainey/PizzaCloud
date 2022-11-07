package pizzas.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pizzas.Ingredient;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class IngredientRepositoryTest {
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void findById() {
        Optional<Ingredient> mozzarella = ingredientRepository.findById("MOZ");
        assertThat(mozzarella.isPresent()).isTrue();
        assertThat(mozzarella.get()).isEqualTo(new Ingredient("MOZ", "Mozzarella", Ingredient.Type.CHEESE));

        Optional<Ingredient> notExist = ingredientRepository.findById("NotExistName");
        assertThat(notExist.isEmpty()).isTrue();
    }
}
