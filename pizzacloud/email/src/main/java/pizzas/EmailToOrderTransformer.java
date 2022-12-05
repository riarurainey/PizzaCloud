package pizzas;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {

    private static final Logger log = LoggerFactory.getLogger(EmailToOrderTransformer.class);

    private static final String SUBJECT_KEYWORDS = "PIZZA ORDER";


    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message message) {
        EmailOrder pizzaOrder = processPayload(message);
        return MessageBuilder.withPayload(pizzaOrder);
    }


    private EmailOrder processPayload(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String email =
                        ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        } catch (MessagingException e) {
            log.error("MessagingException: {}", e);
        } catch (IOException e) {
            log.error("IOException: {}", e);
        }
        return null;
    }

    private EmailOrder parseEmailToOrder(String email, String content) {
        EmailOrder order = new EmailOrder(email);
        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().length() > 0 && line.contains(":")) {
                String[] lineSplit = line.split(":");
                String pizzaName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                List<String> ingredientCodes = new ArrayList<>();
                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());
                    if (code != null) {
                        ingredientCodes.add(code);
                    }
                }

                Pizza pizza = new Pizza(pizzaName);
                pizza.setIngredients(ingredientCodes);
                order.addPizza(pizza);
            }
        }
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            String ucIngredientName = ingredientName.toUpperCase();
            if (LevenshteinDistance.getDefaultInstance()
                    .apply(ucIngredientName, ingredient.getName()) < 3 ||
                    ucIngredientName.contains(ingredient.getName()) ||
                    ingredient.getName().contains(ucIngredientName)) {
                return ingredient.getCode();
            }
        }
        return null;
    }

    private static final Ingredient[] ALL_INGREDIENTS = new Ingredient[]{
            new Ingredient("CLS", "CLASSIC CRUST"),
            new Ingredient("THN", "THIN CRUST"),
            new Ingredient("CHRZ", "CHORIZO"),
            new Ingredient("PEP", "PEPPERONI"),
            new Ingredient("TMTO", "DICED TOMATOES"),
            new Ingredient("ON", "ONION"),
            new Ingredient("BLG", "BULGARIAN PEPPER"),
            new Ingredient("OLV", "OLIVE"),
            new Ingredient("CHED", "CHEDDAR"),
            new Ingredient("MOZ", "MOZZARELLA"),
            new Ingredient("PAR", "PARMESAN"),
            new Ingredient("DRB", "DANISH BLUE"),
            new Ingredient("FET", "FETA"),
            new Ingredient("RNC", "RANCH"),
            new Ingredient("TMT", "TOMATO SAUCE"),
            new Ingredient("MSTR", "MUSTARD SAUCE")};

}
