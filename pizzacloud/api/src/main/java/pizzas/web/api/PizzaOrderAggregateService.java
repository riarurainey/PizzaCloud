package pizzas.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pizzas.Order;
import pizzas.OrderRepository;
import pizzas.Pizza;
import pizzas.PizzaRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaOrderAggregateService {
    private final PizzaRepository pizzaRepository;
    private final OrderRepository orderRepository;

    public Mono<Order> save(Order order) {
        return Mono.just(order)
                .flatMap(pizzaOrder -> {
                    List<Pizza> pizzas = order.getPizzas();
                    order.setPizzas(new ArrayList<>());
                    return pizzaRepository.saveAll(pizzas)
                            .map(pizza -> {
                                order.addPizza(pizza);
                                return order;
                            }).last();
                })
                .flatMap(orderRepository::save);
    }

    public Mono<Order> findById(Long id) {
        return orderRepository.findById(id)
                .flatMap(order -> {
                    return pizzaRepository.findAllById(order.getPizzaIds())
                            .map(pizza -> {
                                order.addPizza(pizza);
                                return order;
                            }).last();
                });
    }
}
