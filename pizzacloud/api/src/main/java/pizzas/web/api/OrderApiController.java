package pizzas.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pizzas.Order;
import pizzas.OrderRepository;
import pizzas.messaging.OrderMessagingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderMessagingService messageService;
    private final EmailOrderService emailOrderService;

    public OrderApiController(OrderRepository orderRepository,
                              OrderMessagingService messageService,
                              EmailOrderService emailOrderService
    ) {
        this.orderRepository = orderRepository;
        this.messageService = messageService;
        this.emailOrderService = emailOrderService;

    }

    @GetMapping(produces = "application/json")
    public Flux<Order> allOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> postOrder(@RequestBody Order order) {
        messageService.sendOrder(order);
        return orderRepository.save(order);
    }

    @PostMapping(path = "fromEmail", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> postOrderFromEmail(@RequestBody Mono<EmailOrder> emailOrder) {
        Mono<Order> order = emailOrderService.convertEmailOrderToDomainOrder(emailOrder);
        order.subscribe(messageService::sendOrder);
        return order.flatMap(orderRepository::save);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public Mono<Order> putOrder(@RequestBody Mono<Order> order) {
        return order.flatMap(orderRepository::save);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Mono<Order> patchOrder(String orderId,
                                  @RequestBody Order patchOrder) {

        return orderRepository.findById(orderId)
                .map(order -> {


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
                    return order;
                })
                .flatMap(orderRepository::save);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {

        }

    }

}
