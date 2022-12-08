package pizzas.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pizzas.Order;

@Service
@Slf4j
public class NoOpOrderMessagingService
        implements OrderMessagingService {

    public void sendOrder(Order order) {
        log.info("Sending order to kitchen: " + order);
    }

}
