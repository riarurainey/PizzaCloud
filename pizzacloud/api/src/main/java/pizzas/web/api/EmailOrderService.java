package pizzas.web.api;

import org.springframework.stereotype.Service;
import pizzas.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import pizzas.web.api.EmailOrder.EmailPizza;

@Service
public class EmailOrderService {
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public EmailOrderService(UserRepository userRepository, IngredientRepository ingredientRepository, PaymentMethodRepository paymentMethodRepository) {
        this.userRepository = userRepository;
        this.ingredientRepository = ingredientRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public Order convertEmailOrderToDomainOrder(EmailOrder emailOrder) {
        User user = userRepository.findByEmail(emailOrder.getEmail());
        PaymentMethod paymentMethod = paymentMethodRepository.findByUserId(user.getId());

        Order order = new Order();
        order.setUser(user);
        order.setCcNumber(paymentMethod.getCcNumber());
        order.setCcCVV(paymentMethod.getCcCVV());
        order.setCcExpiration(paymentMethod.getCcExpiration());
        order.setDeliveryName(user.getFullname());
        order.setDeliveryStreet(user.getStreet());
        order.setDeliveryCity(user.getCity());
        order.setDeliveryState(user.getState());
        order.setDeliveryZip(user.getZip());
        order.setPlacedAt(new Date());

        List<EmailPizza> emailPizzas = emailOrder.getPizzas();
        for (EmailPizza emailPizza : emailPizzas) {
            Pizza pizza = new Pizza();
            pizza.setName(emailPizza.getName());
            List<String> ingredientsIds = emailPizza.getIngredients();
            List<Ingredient> ingredients = new ArrayList<>();
            for (String ingredientId : ingredientsIds) {
                Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
                optionalIngredient.ifPresent(ingredients::add);
            }
            pizza.setIngredients(ingredients);
            order.addPizza(pizza);
        }
        return order;
    }
}
