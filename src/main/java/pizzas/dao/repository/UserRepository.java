package pizzas.dao.repository;

import org.springframework.data.repository.CrudRepository;
import pizzas.dao.model.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

}
