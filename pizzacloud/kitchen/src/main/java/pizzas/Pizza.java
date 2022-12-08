package pizzas;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Pizza {

    private String name;
    private Date createdAt;
    private List<Ingredient> ingredients;

}
