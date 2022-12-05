package pizzas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {
    private final RestTemplate rest;

    @Value("${spring.pizzacloud.api.url}")
    private String url;

    public OrderSubmitMessageHandler(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public Object handle(EmailOrder order, MessageHeaders headers) {
        rest.postForObject(url, order, String.class);
        return null;
    }
}
