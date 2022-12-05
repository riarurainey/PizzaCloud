package pizzas.kitchen.messaging.jms;

import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pizzas.Order;
import pizzas.kitchen.OrderReceiver;

@Profile("jms-template")
@Component
public class JmsOrderReceiver implements OrderReceiver {
    private final JmsTemplate jms;

    public JmsOrderReceiver(JmsTemplate jms) {
        this.jms = jms;

    }

    @Override
    public Order receiveOrder() {
        return (Order) jms.receiveAndConvert("pizzacloud.order.queue");
    }
}
