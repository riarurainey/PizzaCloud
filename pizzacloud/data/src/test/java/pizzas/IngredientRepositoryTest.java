package pizzas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import pizzas.Ingredient.Type;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataCassandraTest
public class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        Flux<Ingredient> deleteAndInsert = ingredientRepository.deleteAll()
                .thenMany(ingredientRepository.saveAll(
                        Flux.just(new Ingredient("CLS", "Classic Crust", Type.WRAP),
                                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                                new Ingredient("PEP", "Pepperoni", Type.PROTEIN)
                )));

        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetch() {
        StepVerifier.create(ingredientRepository.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    assertThat(ingredients).hasSize(3);
                    assertThat(ingredients).contains(
                            new Ingredient("CLS", "Classic Crust", Type.WRAP));
                    assertThat(ingredients).contains(
                            new Ingredient("CHED", "Cheddar", Type.CHEESE));
                    assertThat(ingredients).contains(
                            new Ingredient("PEP", "Pepperoni", Type.PROTEIN));

                })
                .verifyComplete();

        StepVerifier.create(ingredientRepository.findById("CLS"))
                .assertNext(ingredient -> {
                    ingredient.equals(new Ingredient("CLS", "Classic Crust", Type.WRAP));
                });
    }
}
