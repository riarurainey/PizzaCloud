package pizzas;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private final User user;
    private final String ccNumber;
    private final String ccCVV;
    private final String ccExpiration;
}
