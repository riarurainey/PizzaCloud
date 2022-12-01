package pizzas;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @ManyToMany(targetEntity = Ingredient.class)
    @ToString.Exclude
    private List<Ingredient> ingredients = new ArrayList<>();

    private Date createdAt;

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }


    @PrePersist
    void createdAt() {
        createdAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return Objects.equals(id, pizza.id)
                && Objects.equals(name, pizza.name)
                && Objects.equals(ingredients, pizza.ingredients)
                && Objects.equals(createdAt, pizza.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, createdAt);
    }
}
