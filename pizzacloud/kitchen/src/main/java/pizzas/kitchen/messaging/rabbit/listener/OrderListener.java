package pizzas.kitchen.messaging.rabbit.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pizzas.PizzaOrder;
import pizzas.kitchen.KitchenUi;

@Profile("rabbitmq-listener")
@Component
public class OrderListener {
    private final KitchenUi ui;

    public OrderListener(KitchenUi ui) {
        this.ui = ui;
    }

    @RabbitListener(queues = "pizzacloud.order.queue")
    public void receiveOrder(PizzaOrder pizzaOrder) {
        ui.displayOrder(pizzaOrder);
    }

}
