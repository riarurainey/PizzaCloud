package pizzas.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pizzas.PizzaOrder;
import pizzas.messaging.pizzas.messaging.OrderMessagingService;

import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {
    private final JmsTemplate jms;


    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;

    }


    @Override
    public void sendOrder(PizzaOrder pizzaOrder) {
        jms.convertAndSend("pizzacloud.order.queue", pizzaOrder);
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
