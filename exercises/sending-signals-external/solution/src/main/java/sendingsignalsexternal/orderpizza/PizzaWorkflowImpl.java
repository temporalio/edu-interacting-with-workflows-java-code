package sendingsignalsexternal.orderpizza;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.failure.ApplicationFailure;

import sendingsignalsexternal.model.Address;
import sendingsignalsexternal.model.Bill;
import sendingsignalsexternal.model.Customer;
import sendingsignalsexternal.model.Distance;
import sendingsignalsexternal.model.OrderConfirmation;
import sendingsignalsexternal.model.Pizza;
import sendingsignalsexternal.model.PizzaOrder;
import sendingsignalsexternal.exceptions.InvalidChargeAmountException;
import sendingsignalsexternal.exceptions.OutOfServiceAreaException;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;

public class PizzaWorkflowImpl implements PizzaWorkflow {

  public static final Logger logger = Workflow.getLogger(PizzaWorkflowImpl.class);

  private boolean fulfilled;

  ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

  private final PizzaActivities activities = Workflow.newActivityStub(PizzaActivities.class, options);

  @Override
  public OrderConfirmation orderPizza(PizzaOrder order) {

    String orderNumber = order.getOrderNumber();
    Customer customer = order.getCustomer();
    List<Pizza> items = order.getItems();
    boolean isDelivery = order.isDelivery();
    Address address = order.getAddress();

    logger.info("orderPizza Workflow Invoked");

    int totalPrice = 0;
    for (Pizza pizza : items) {
      totalPrice += pizza.getPrice();
    }

    Distance distance;
    try {
      distance = activities.getDistance(address);
    } catch (NullPointerException e) {
      logger.error("Unable to get distance");
      throw new NullPointerException("Unable to get distance");
    }

    if (isDelivery && (distance.getKilometers() > 25)) {
      logger.error("Customer lives outside the service area");
      throw ApplicationFailure.newFailure("Customer lives outside the service area",
          OutOfServiceAreaException.class.getName());
    }

    logger.info("distance is {}", distance.getKilometers());

    Workflow.await(() -> this.fulfilled);

    OrderConfirmation confirmation;

    if (this.fulfilled) {
      Bill bill = new Bill(customer.getCustomerID(), orderNumber, "Pizza", totalPrice);

      try {
        confirmation = activities.sendBill(bill);
        logger.info("Bill sent to customer {}", customer.getCustomerID());
      } catch (InvalidChargeAmountException e) {
        logger.error("Unable to bill customer");
        throw Workflow.wrap(new InvalidChargeAmountException("Unable to bill customer"));
      }
    } else {
      confirmation = null;
      logger.info("Order was not fulfilled. Not billing the customer.");
    }

    return confirmation;

  }

  @Override
  public void fulfillOrderSignal(boolean bool) {
    this.fulfilled = bool;
  }
}
