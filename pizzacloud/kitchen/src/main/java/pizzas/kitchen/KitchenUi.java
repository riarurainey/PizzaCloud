package pizzas.kitchen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pizzas.PizzaOrder;

@Slf4j
@Component
public class KitchenUi {
    public void displayOrder(PizzaOrder pizzaOrder) {
        log.info("RECEIVED ORDER:  " + pizzaOrder);
    }
}
