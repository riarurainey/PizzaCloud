package pizzas.kitchen;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pizzas.PizzaOrder;

@Profile({"jms-template", "rabbitmq-template"})
@Controller
@RequestMapping("/orders")
public class OrderReceiverController {



    private final OrderReceiver orderReceiver;

    public OrderReceiverController(OrderReceiver orderReceiver) {
        this.orderReceiver = orderReceiver;
    }

    @GetMapping("/receive")
    public String receiveOrder(Model model) {
        PizzaOrder pizzaOrder = orderReceiver.receiveOrder();
        if (pizzaOrder != null) {
            model.addAttribute("order", pizzaOrder);
            return "receiveOrder";
        }
        return "noOrder";
    }
}
