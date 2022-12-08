package pizzas.web.api;

import org.springframework.stereotype.Service;

import java.util.List;

import pizzas.Ingredient;
import pizzas.PaymentMethod;
import pizzas.User;
import pizzas.Pizza;
import pizzas.IngredientRepository;
import pizzas.PaymentMethodRepository;
import pizzas.UserRepository;
import pizzas.Order;

import pizzas.web.api.EmailOrder.EmailPizza;
import reactor.core.publisher.Mono;

@Service
public class EmailOrderService {
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public EmailOrderService(UserRepository userRepository,
                             IngredientRepository ingredientRepository,
                             PaymentMethodRepository paymentMethodRepository) {
        this.userRepository = userRepository;
        this.ingredientRepository = ingredientRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public Mono<Order> convertEmailOrderToDomainOrder(Mono<EmailOrder> emailOrder) {

        return emailOrder.flatMap(eOrder -> {
            Mono<User> userMono = userRepository.findByEmail(eOrder.getEmail());

            Mono<PaymentMethod> paymentMono = userMono.flatMap(user -> {
                return paymentMethodRepository.findByUserId(user.getId());
            });
            return Mono.zip(userMono, paymentMono)
                    .flatMap(tuple -> {
                        User user = tuple.getT1();
                        PaymentMethod paymentMethod = tuple.getT2();
                        Order order = new Order();
                        order.setCcNumber(paymentMethod.getCcNumber());
                        order.setCcCVV(paymentMethod.getCcCVV());
                        order.setCcExpiration(paymentMethod.getCcExpiration());
                        order.setDeliveryName(user.getFullname());
                        order.setDeliveryStreet(user.getStreet());
                        order.setDeliveryCity(user.getCity());
                        order.setDeliveryState(user.getState());
                        order.setDeliveryZip(user.getZip());

                        return emailOrder.map(eOrd -> {
                            List<EmailPizza> emailPizzas = eOrd.getPizzas();
                            for (EmailPizza emailPizza : emailPizzas) {
                                Pizza pizza = new Pizza();
                                pizza.setName(emailPizza.getName());

                                List<String> ingredientIds = emailPizza.getIngredients();
                                for (String ingredientId : ingredientIds) {
                                    Mono<Ingredient> ingredientMono = ingredientRepository.findBySlug(ingredientId);
                                    ingredientMono.subscribe(ingredient ->
                                            pizza.addIngredient(ingredient));
                                }
                                order.addPizza(pizza);
                            }
                            return order;
                        });

                    });
        });

    }
}
