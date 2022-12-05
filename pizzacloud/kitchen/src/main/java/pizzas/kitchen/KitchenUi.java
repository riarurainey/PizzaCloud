package pizzas.kitchen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pizzas.Order;

@Slf4j
@Component
public class KitchenUi {
    public void displayOrder(Order order) {
        log.info("RECEIVED ORDER:  " + order);
    }
}
