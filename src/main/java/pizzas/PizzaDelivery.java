package pizzas;

import lombok.Data;

@Data
public class PizzaDelivery {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
}
