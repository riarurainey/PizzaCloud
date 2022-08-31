package pizzas.models;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

@Data
@UserDefinedType("pizza")
public class PizzaUDT {
    private final String name;
    private final List<IngredientUDT> ingredients;

}


