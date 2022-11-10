package pizzas.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import pizzas.dao.model.PizzaOrder;
import pizzas.dao.model.User;
import pizzas.dao.repository.OrderRepository;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("pizzaOrder")

public class OrderController {
    private final OrderRepository orderRepository;

    @Min(value = 5, message = "must be between 5 and 25")
    @Max(value = 25, message = "must be between 5 and 25")
    @Value("${spring.pizza.orders.pageSize: 20}")
    private int pageSize;


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
        return "redirect:/orders";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, pageSize);
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";

    }
}