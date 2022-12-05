package pizzas.web.api;

import lombok.Data;

import java.util.List;

@Data
public class EmailOrder {
    private String email;
    private List<EmailPizza> pizzas;

    @Data
    public static class EmailPizza {
        private String name;
        private List<String> ingredients;
    }
}
