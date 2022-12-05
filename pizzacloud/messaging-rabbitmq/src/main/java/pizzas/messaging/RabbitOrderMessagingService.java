package pizzas.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pizzas.Order;
import pizzas.messaging.pizzas.messaging.OrderMessagingService;


@Service
public class RabbitOrderMessagingService implements OrderMessagingService {
    private final RabbitTemplate rabbitTemplate;

    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        rabbitTemplate.convertAndSend("pizzacloud.order.queue", order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties properties = message.getMessageProperties();
                properties.setHeader("X_ORDER_SOURCE", "WEB");
                return message;
            }
        });

    }
}
