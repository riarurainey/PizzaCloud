package pizzas.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pizzas.data.service.OrderAdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private OrderAdminService orderAdminService;

    public AdminController(OrderAdminService orderAdminService) {
        this.orderAdminService = orderAdminService;
    }

    @GetMapping
    public String showAdminPage() {
        return "admin";
    }

    @PostMapping("/deleteOrders")
    public String deleteOrders() {
        orderAdminService.deleteAllOrders();
        return "redirect:/admin";
    }
}

