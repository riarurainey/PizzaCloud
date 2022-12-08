package pizzas.web.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.annotation.DirtiesContext;
import pizzas.Order;
import pizzas.OrderRepository;
import pizzas.Pizza;
import pizzas.PizzaRepository;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@DirtiesContext
public class PizzaOrderAggregateServiceTests {

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    OrderRepository orderRepository;

    PizzaOrderAggregateService service;


    @BeforeEach
    public void setup() {
        this.service = new PizzaOrderAggregateService(pizzaRepository, orderRepository);

    }

    @Test
    public void shouldSaveAndFetchOrders() {
        Order newOrder = new Order();
        newOrder.setDeliveryName("Test Customer");
        newOrder.setDeliveryStreet("1234 North Street");
        newOrder.setDeliveryCity("Notrees");
        newOrder.setDeliveryState("TX");
        newOrder.setDeliveryZip("79759");
        newOrder.setCcNumber("4111111111111111");
        newOrder.setCcExpiration("12/24");
        newOrder.setCcCVV("123");

        newOrder.addPizza(new Pizza("Test Pizza One"));
        newOrder.addPizza(new Pizza("Test Pizza Two"));

        StepVerifier.create(service.save(newOrder))
                .assertNext(this::assertOrder)
                .verifyComplete();

        StepVerifier.create(service.findById(1L))
                .assertNext(this::assertOrder)
                .verifyComplete();

    }

    private void assertOrder(Order savedOrder) {

        assertThat(savedOrder.getId()).isEqualTo(1L);
        assertThat(savedOrder.getDeliveryName()).isEqualTo("Test Customer");
        assertThat(savedOrder.getDeliveryName()).isEqualTo("Test Customer");
        assertThat(savedOrder.getDeliveryStreet()).isEqualTo("1234 North Street");
        assertThat(savedOrder.getDeliveryCity()).isEqualTo("Notrees");
        assertThat(savedOrder.getDeliveryState()).isEqualTo("TX");
        assertThat(savedOrder.getDeliveryZip()).isEqualTo("79759");
        assertThat(savedOrder.getCcNumber()).isEqualTo("4111111111111111");
        assertThat(savedOrder.getCcExpiration()).isEqualTo("12/24");
        assertThat(savedOrder.getCcCVV()).isEqualTo("123");
        assertThat(savedOrder.getPizzaIds()).hasSize(2);
        assertThat(savedOrder.getPizzas().get(0).getId()).isEqualTo(1L);
        assertThat(savedOrder.getPizzas().get(0).getName())
                .isEqualTo("Test Pizza One");
        assertThat(savedOrder.getPizzas().get(1).getId()).isEqualTo(2L);
        assertThat(savedOrder.getPizzas().get(1).getName())
                .isEqualTo("Test Pizza Two");
    }
}
