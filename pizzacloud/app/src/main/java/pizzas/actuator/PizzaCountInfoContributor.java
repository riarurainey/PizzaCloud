package pizzas.actuator;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import pizzas.PizzaRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class PizzaCountInfoContributor implements InfoContributor {
    private final PizzaRepository pizzaRepository;

    public PizzaCountInfoContributor(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public void contribute(Builder builder) {
        long pizzaCount = pizzaRepository.count().block();
        Map<String, Object> pizzaMap = new HashMap<>();
        pizzaMap.put("count", pizzaCount);
        builder.withDetail("pizza-stats", pizzaMap);

    }
}
