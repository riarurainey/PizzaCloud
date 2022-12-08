package pizzas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import pizzas.Ingredient.Type;

import reactor.test.StepVerifier;

@DataR2dbcTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        orderRepository.deleteAll().subscribe();
    }


    @Test
    public void shouldSaveAndFetchOrders() {
        Order order = createOrder();

        StepVerifier.create(orderRepository.save(order))
                .expectNext(order)
                .verifyComplete();

        StepVerifier
                .create(orderRepository.findById(order.getId()))
                .expectNext(order)
                .verifyComplete();

        StepVerifier
                .create(orderRepository.findAll())
                .expectNext(order)
                .verifyComplete();
    }


    private Order createOrder() {
        Order order = new Order();
        order.setDeliveryName("Test Customer");
        order.setDeliveryStreet("1234 North Street");
        order.setDeliveryCity("Notrees");
        order.setDeliveryState("TX");
        order.setDeliveryZip("79759");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("12/23");
        order.setCcCVV("123");
        Pizza pizza1 = new Pizza();
        pizza1.setName("Test Pizza One");

        pizza1.addIngredient(new Ingredient("CLS", "Classic Crust", Type.WRAP));
        pizza1.addIngredient(new Ingredient("CHED", "Cheddar", Type.CHEESE));
        pizza1.addIngredient(new Ingredient("PEP", "Pepperoni", Type.PROTEIN));
        order.addPizza(pizza1);

        Pizza pizza2 = new Pizza();
        pizza2.setName("Test Pizza Two");

        pizza2.addIngredient(new Ingredient("THN", "Thin Crust", Type.WRAP));
        pizza2.addIngredient(new Ingredient("CHRZ", "Chorizo", Type.PROTEIN));
        pizza2.addIngredient(new Ingredient("TMT", "Tomato Sauce", Type.SAUCE));
        order.addPizza(pizza2);
        return order;
    }
}
