package pizzas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class PizzaOrderEmailIntegrationConfig {

    @Bean
    public StandardIntegrationFlow pizzaOrderEmailFlow(
            EmailProperties emailProp,
            EmailToOrderTransformer emailToOrderTransformer,
            OrderSubmitMessageHandler orderSubmitHandler) {
        return IntegrationFlows
                .from(Mail.imapInboundAdapter(emailProp.getImapUrl()),
                        e -> e.poller(
                                Pollers.fixedDelay(emailProp.getPollRate())))
                .transform(emailToOrderTransformer)
                .handle(orderSubmitHandler)
                .get();


    }
}
