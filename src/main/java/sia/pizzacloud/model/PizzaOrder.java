package sia.pizzacloud.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PizzaOrder {
    PizzaDelivery pizzaDelivery;
    PizzaPayment pizzaPayment;
    private List<Pizza> pizzaList = new ArrayList<>();

    public void addPizza(Pizza pizza) {
        pizzaList.add(pizza);
    }

}
