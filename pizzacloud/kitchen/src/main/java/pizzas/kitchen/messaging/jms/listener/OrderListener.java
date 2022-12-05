package pizzas.kitchen.messaging.jms.listener;

import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pizzas.kitchen.KitchenUi;
import pizzas.Order;

@Profile("jms-listener")
@Component
public class OrderListener {
    private final KitchenUi ui;

    public OrderListener(KitchenUi ui) {
        this.ui = ui;
    }

    @JmsListener(destination = "pizzacloud.order.queue")
    public void receiveOrder(Order order) {
        ui.displayOrder(order);
    }

}
