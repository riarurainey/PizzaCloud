package pizzas.data;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pizzas.models.IngredientRef;
import pizzas.models.Pizza;
import pizzas.models.PizzaOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    @Transactional
    public PizzaOrder save(PizzaOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "INSERT INTO Pizza_Order "
                        + "(delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number,  "
                        + "cc_expiration, cc_cvv, placed_at) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        order.setPlaceAt(new Date());
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryCity(),
                                order.getDeliveryState(),
                                order.getDeliveryZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlaceAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        order.setId(orderId);

        List<Pizza> pizzas = order.getPizzas();
        int i = 0;
        for (Pizza pizza : pizzas) {
            savePizza(orderId, i++, pizza);
        }
        return order;

    }

    private long savePizza(Long orderId, int orderKey, Pizza pizza) {
        pizza.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "INSERT INTO Pizza "
                        + "(name, created_at, pizza_order, pizza_order_key) "
                        + "VALUES (?, ?, ?, ?)",
                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        pizza.getName(),
                        pizza.getCreatedAt(),
                        orderId,
                        orderKey));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long pizzaId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        pizza.setId(pizzaId);

        saveIngredientRefs(pizzaId, pizza.getIngredients());
        return pizzaId;

    }

    private void saveIngredientRefs(Long pizzaId, List<IngredientRef> ingredientsRef) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientsRef) {
            jdbcOperations.update(
                    "INSERT INTO Ingredient_Ref(ingredient, pizza, pizza_key) "
                            + "VALUES (?, ?, ?)",
                    ingredientRef.getIngredient(), pizzaId, key++);
        }
    }
}
