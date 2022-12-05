package pizzas.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzas.Order;
import pizzas.OrderRepository;
import pizzas.messaging.pizzas.messaging.OrderMessagingService;


@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderMessagingService messageService;
    private final EmailOrderService emailOrderService;

    public OrderApiController(OrderRepository orderRepository, OrderMessagingService messageService, EmailOrderService emailOrderService) {
        this.orderRepository = orderRepository;
        this.messageService = messageService;
        this.emailOrderService = emailOrderService;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        messageService.sendOrder(order);
        return orderRepository.save(order);
    }

    @PostMapping(path = "fromEmail", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrderFromEmail(@RequestBody EmailOrder emailOrder) {
        Order order = emailOrderService.convertEmailOrderToDomainOrder(emailOrder);
        messageService.sendOrder(order);
        return orderRepository.save(order);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public Order putOrder(@PathVariable("orderId") Long orderId,
                          @RequestBody Order order) {
        order.setId(orderId);
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody Order patchOrder) {

        Order order = orderRepository.findById(orderId).get();

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
