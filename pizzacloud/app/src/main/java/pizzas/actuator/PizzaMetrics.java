package pizzas.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;
import pizzas.Ingredient;
import pizzas.Pizza;

import java.util.List;

@Component
public class PizzaMetrics extends AbstractRepositoryEventListener<Pizza> {
    private final MeterRegistry meterRegistry;

    public PizzaMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

    }


    @Override
    protected void onAfterCreate(Pizza pizza) {
        List<Ingredient> ingredients = pizza.getIngredients();
        for (Ingredient ingredient : ingredients) {
            meterRegistry.counter("pizzacloud",
                    "ingredient", ingredient.getId()).increment();
        }
    }
}
