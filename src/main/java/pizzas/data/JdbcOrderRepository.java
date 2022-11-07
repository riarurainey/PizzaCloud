package pizzas.data;

import org.springframework.asm.Type;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pizzas.IngredientRef;
import pizzas.Pizza;
import pizzas.PizzaOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

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

        order.setPlacedAt(new Date());
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
                                order.getPlacedAt()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
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
        long pizzaId = keyHolder.getKey().longValue();
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

    @Override
    public Optional<PizzaOrder> findById(Long id) {
        try {
            PizzaOrder order = jdbcOperations.queryForObject(
                    "select id, delivery_name, delivery_street, delivery_city, "
                            + "delivery_state, delivery_zip, cc_number, cc_expiration, "
                            + "cc_cvv, placed_at from Pizza_Order where id=?",
                    (row, rowNum) -> {
                        PizzaOrder pizzaOrder = new PizzaOrder();
                        pizzaOrder.setId(row.getLong("id"));
                        pizzaOrder.setDeliveryName(row.getString("delivery_name"));
                        pizzaOrder.setDeliveryStreet(row.getString("delivery_street"));
                        pizzaOrder.setDeliveryCity(row.getString("delivery_city"));
                        pizzaOrder.setDeliveryState(row.getString("delivery_state"));
                        pizzaOrder.setDeliveryZip(row.getString("delivery_zip"));
                        pizzaOrder.setCcNumber(row.getString("cc_number"));
                        pizzaOrder.setCcExpiration(row.getString("cc_expiration"));
                        pizzaOrder.setCcCVV(row.getString("cc_cvv"));
                        pizzaOrder.setPlacedAt(new Date(row.getTimestamp("placed_at").getTime()));
                        pizzaOrder.setPizzas(findPizzasByOrderId(row.getLong("id")));
                        return pizzaOrder;
                    }, id);
            return Optional.of(order);

        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    private List<Pizza> findPizzasByOrderId(long orderId) {
        return jdbcOperations.query(
                "select id, name, created_at from Pizza "
                        + "where pizza_order=? order by pizza_order_key",
                (row, rowNum) -> {
                    Pizza pizza = new Pizza();
                    pizza.setId(row.getLong("id"));
                    pizza.setName(row.getString("name"));
                    pizza.setCreatedAt(new Date(row.getTimestamp("created_at").getTime()));
                    pizza.setIngredients(findIngredientsByPizzaId(row.getLong("id")));
                    return pizza;
                },
                orderId);
    }

    private List<IngredientRef> findIngredientsByPizzaId(long pizzaId) {
        return jdbcOperations.query(
                "select ingredient from Ingredient_Ref "
                        + "where pizza = ? order by pizza_key",
                (row, rowNum) -> {
                    return new IngredientRef(row.getString("ingredient"));
                },
                pizzaId);
    }

}
