package pizzas.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pizzas.dao.model.Ingredient;
import pizzas.dao.model.Pizza;
import pizzas.dao.model.PizzaOrder;
import pizzas.dao.repository.OrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void saveOrderWithTwoPizzas() {
        PizzaOrder order = new PizzaOrder();
        order.setDeliveryName("Name");
        order.setDeliveryStreet("DeliveryStreet");
        order.setDeliveryCity("City");
        order.setDeliveryState("TS");
        order.setDeliveryZip("11111");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("10/24");
        order.setCcCVV("111");

        Pizza pizza1 = new Pizza();
        pizza1.setName("First Pizza");
        pizza1.addIngredient(new Ingredient("MOZ", "Mozzarella", Ingredient.Type.CHEESE));
        pizza1.addIngredient(new Ingredient("CHRZ", "Chorizo", Ingredient.Type.PROTEIN));
        pizza1.addIngredient(new Ingredient("MSTR", "Mustard sauce", Ingredient.Type.SAUCE));

        order.addPizza(pizza1);

        Pizza pizza2 = new Pizza();
        pizza2.setName("Second Pizza");
        pizza2.addIngredient(new Ingredient("PEP", "Pepperoni", Ingredient.Type.PROTEIN));
        pizza2.addIngredient(new Ingredient("OLV", "Olive", Ingredient.Type.VEGGIES));
        pizza2.addIngredient(new Ingredient("TMT", "Tomato Sauce", Ingredient.Type.SAUCE));
        order.addPizza(pizza2);

        PizzaOrder saveOrder = orderRepository.save(order);
        assertThat(saveOrder.getId()).isNotNull();

        PizzaOrder fetchedOrder = orderRepository.findById(saveOrder.getId()).get();
        assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Name");
        assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("DeliveryStreet");
        assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("City");
        assertThat(fetchedOrder.getDeliveryState()).isEqualTo("TS");
        assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("11111");
        assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
        assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/24");
        assertThat(fetchedOrder.getCcCVV()).isEqualTo("111");
        assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(saveOrder.getPlacedAt().getTime());

        List<Pizza> pizzas = fetchedOrder.getPizzas();
        assertThat(pizzas.size()).isEqualTo(2);

    }

}
