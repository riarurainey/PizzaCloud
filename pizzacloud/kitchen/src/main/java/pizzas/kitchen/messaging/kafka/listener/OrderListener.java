package pizzas.kitchen.messaging.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pizzas.Order;
import pizzas.kitchen.KitchenUi;

@Profile("kafka-listener")
@Component
@Slf4j
public class OrderListener {

    private final KitchenUi ui;

    public OrderListener(KitchenUi ui) {
        this.ui = ui;
    }

    @KafkaListener(topics = "pizzacloud.orders.topic")
    public void handle(Order order, ConsumerRecord<String, Order> record) {
        log.info("Received from partition {} with timestamp {}", record.partition(), record.timestamp());
        ui.displayOrder(order);
    }

}
