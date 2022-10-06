package pizzas.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import pizzas.data.OrderRepository;
import pizzas.model.PizzaOrder;
import pizzas.model.User;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("pizzaOrder")

public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute PizzaOrder pizzaOrder) {
        if (pizzaOrder.getDeliveryName() == null) {
            pizzaOrder.setDeliveryName(user.getFullname());
        }
        if (pizzaOrder.getDeliveryStreet() == null) {
            pizzaOrder.setDeliveryStreet(user.getStreet());
        }
        if (pizzaOrder.getDeliveryCity() == null) {
            pizzaOrder.setDeliveryCity(user.getCity());
        }
        if (pizzaOrder.getDeliveryState() == null) {
            pizzaOrder.setDeliveryState(user.getState());
        }
        if (pizzaOrder.getDeliveryZip() == null) {
            pizzaOrder.setDeliveryZip(user.getZip());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid PizzaOrder pizzaOrder, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: {}", pizzaOrder);
        pizzaOrder.setUser(user);
        orderRepository.save(pizzaOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
