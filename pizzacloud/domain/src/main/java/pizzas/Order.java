package pizzas;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private UUID id = Uuids.timeBased();

    private Date placedAt = new Date();

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    @Column("pizzas")
    private List<PizzaUDT> pizzas = new ArrayList<>();

    public void addPizza(Pizza pizza) {
        this.addPizza(new PizzaUDT(pizza.getName(), pizza.getIngredients()));
    }

    public void addPizza(PizzaUDT tacoUDT) {
        this.pizzas.add(tacoUDT);
    }

}