package sendingsignalsexternal.fulfillorder;

import sendingsignalsexternal.model.PizzaOrder;
import sendingsignalsexternal.model.Pizza;

import io.temporal.activity.Activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FulfillOrderActivitiesImpl implements FulfillOrderActivities {

  private static final Logger logger = LoggerFactory.getLogger(FulfillOrderActivitiesImpl.class);

  public void makePizzas(PizzaOrder order) {

    logger.info("Starting to make pizzas for order {}", order.getOrderNumber());

    for (Pizza pizza : order.getItems()) {
      logger.info("Making pizza: {}", pizza.getDescription());

      // Simulate the time taken to make a pizza.
    }

    logger.info("All pizzas for order {} are ready!", order.getOrderNumber());
  }

  public void deliverPizzas(PizzaOrder order) {
    logger.info("Starting delivery {} to {}", order.getOrderNumber(), order.getAddress());

    // Simulate the time to make delivery.
  }

}
