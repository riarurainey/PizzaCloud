package pizzas.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pizzas.PizzaOrder;
import pizzas.messaging.pizzas.messaging.OrderMessagingService;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService {

    private final KafkaTemplate<String, PizzaOrder> kafkaTemplate;

    public KafkaOrderMessagingService(KafkaTemplate<String, PizzaOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(PizzaOrder pizzaOrder) {
        kafkaTemplate.sendDefault(pizzaOrder);

    }
}
