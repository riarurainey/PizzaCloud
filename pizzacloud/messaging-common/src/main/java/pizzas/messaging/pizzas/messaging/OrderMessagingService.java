package pizzas.messaging.pizzas.messaging;


import pizzas.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}

