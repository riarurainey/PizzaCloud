package pizzas;

import org.springframework.data.repository.CrudRepository;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {
    PaymentMethod findByUserId(Long userId);
}
