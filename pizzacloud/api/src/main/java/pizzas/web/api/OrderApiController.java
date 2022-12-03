package pizzas.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzas.OrderRepository;
import pizzas.PizzaOrder;
import pizzas.messaging.pizzas.messaging.OrderMessagingService;


@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://pizza-cloud:8080")
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderMessagingService messageService;

    public OrderApiController(OrderRepository orderRepository, OrderMessagingService messageService) {
        this.orderRepository = orderRepository;
        this.messageService = messageService;
    }

    @GetMapping(produces = "application/json")
    public Iterable<PizzaOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)

    public PizzaOrder postOrder(@RequestBody PizzaOrder pizzaOrder) {
        messageService.sendOrder(pizzaOrder);
        return orderRepository.save(pizzaOrder);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public PizzaOrder putOrder(@PathVariable("orderId") Long orderId,
                               @RequestBody PizzaOrder pizzaOrder) {
        pizzaOrder.setId(orderId);
        return orderRepository.save(pizzaOrder);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public PizzaOrder patchOrder(@PathVariable("orderId") Long orderId,
                                 @RequestBody PizzaOrder patchOrder) {

        PizzaOrder order = orderRepository.findById(orderId).get();

        if (patchOrder.getDeliveryName() != null) {
            order.setDeliveryName(patchOrder.getDeliveryName());
        }
        if (patchOrder.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patchOrder.getDeliveryStreet());
        }
        if (patchOrder.getDeliveryCity() != null) {
            order.setDeliveryCity(patchOrder.getDeliveryCity());
        }
        if (patchOrder.getDeliveryState() != null) {
            order.setDeliveryState(patchOrder.getDeliveryState());
        }
        if (patchOrder.getDeliveryZip() != null) {
            order.setDeliveryZip(patchOrder.getDeliveryZip());
        }
        if (patchOrder.getCcNumber() != null) {
            order.setCcNumber(patchOrder.getCcNumber());
        }
        if (patchOrder.getCcExpiration() != null) {
            order.setCcExpiration(patchOrder.getCcExpiration());
        }
        if (patchOrder.getCcCVV() != null) {
            order.setCcCVV(patchOrder.getCcCVV());
        }

        return orderRepository.save(order);

    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {

        }

    }

}
