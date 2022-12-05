package pizzas;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@RestResource(rel = "orders", path = "orders")
@Table(name="pizza_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "(^0[1-9]|1[0-1])([\\/])([2-9][0-9])$", message = "Invalid expiration date. Format: MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private Date placedAt;

    @ManyToOne()
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Pizza> pizzas = new ArrayList<>();

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    @PrePersist
    void placedAt() {
        placedAt = new Date();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return Objects.equals(id, that.id)
                && Objects.equals(deliveryName, that.deliveryName)
                && Objects.equals(deliveryStreet, that.deliveryStreet)
                && Objects.equals(deliveryCity, that.deliveryCity)
                && Objects.equals(deliveryState, that.deliveryState)
                && Objects.equals(deliveryZip, that.deliveryZip)
                && Objects.equals(ccNumber, that.ccNumber)
                && Objects.equals(ccExpiration, that.ccExpiration)
                && Objects.equals(ccCVV, that.ccCVV)
                && Objects.equals(placedAt, that.placedAt)
                && Objects.equals(user, that.user)
                && Objects.equals(pizzas, that.pizzas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryName, deliveryStreet, deliveryCity,
                deliveryState, deliveryZip, ccNumber,
                ccExpiration, ccCVV, placedAt, user, pizzas);
    }
}