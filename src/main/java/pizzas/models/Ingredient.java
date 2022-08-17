package pizzas.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@RequiredArgsConstructor
public class Ingredient implements Persistable<String> {

    @Id
    private final String id;
    private final String name;
    private final Type type;

    @Override
    public boolean isNew() {
        return true;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}


