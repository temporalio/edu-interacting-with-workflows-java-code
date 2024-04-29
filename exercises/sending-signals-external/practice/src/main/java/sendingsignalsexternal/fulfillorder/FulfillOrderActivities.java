package sendingsignalsexternal.fulfillorder;

import io.temporal.activity.ActivityInterface;

import sendingsignalsexternal.model.PizzaOrder;

@ActivityInterface
public interface FulfillOrderActivities {

  void makePizzas(PizzaOrder order);

  void deliverPizzas(PizzaOrder order);

}
