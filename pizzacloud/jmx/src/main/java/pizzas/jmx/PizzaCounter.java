package pizzas.jmx;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

import javax.management.Notification;

import pizzas.Pizza;
import pizzas.PizzaRepository;


@Service
@ManagedResource
public class PizzaCounter

        extends AbstractRepositoryEventListener<Pizza>
        implements NotificationPublisherAware {

    private AtomicLong counter;
    private NotificationPublisher np;

    @Override
    public void setNotificationPublisher(NotificationPublisher np) {
        this.np = np;
    }

    public PizzaCounter(PizzaRepository pizzaRepository) {
        pizzaRepository
                .count()
                .subscribe(initialCount -> {
                    this.counter = new AtomicLong(initialCount);
                });
    }

    @Override
    protected void onAfterCreate(Pizza entity) {
        counter.incrementAndGet();
    }

    @ManagedAttribute
    public long getPizzaCount() {
        return counter.get();
    }


    @ManagedOperation
    public long increment(long delta) {
        long before = counter.get();
        long after = counter.addAndGet(delta);
        if ((after / 100) > (before / 100)) {
            Notification notification = new Notification(
                    "pizza.count", this,
                    before, after + "th pizza created!");
            np.sendNotification(notification);
        }
        return after;
    }

}
