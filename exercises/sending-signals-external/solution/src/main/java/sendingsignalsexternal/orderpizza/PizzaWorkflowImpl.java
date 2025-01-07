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
import java.util.Optional;

import org.slf4j.Logger;

public class PizzaWorkflowImpl implements PizzaWorkflow {

  public static final Logger logger = Workflow.getLogger(PizzaWorkflowImpl.class);

  private boolean fulfilled;
  private boolean signalProcessed;

  ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

  private final PizzaActivities activities = Workflow.newActivityStub(PizzaActivities.class, options);

  @Override
  public Optional<OrderConfirmation> orderPizza(PizzaOrder order) {

    String orderNumber = order.getOrderNumber();
    Customer customer = order.getCustomer();
    List<Pizza> items = order.getItems();
    boolean isDelivery = order.isDelivery();
    Address address = order.getAddress();
    signalProcessed = false;

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

    Workflow.await(Duration.ofSeconds(3),() -> this.signalProcessed);

    OrderConfirmation confirmation;

    if (this.fulfilled) {
      Bill bill = new Bill(customer.getCustomerID(), orderNumber, "Pizza", totalPrice);

      try {
        confirmation = activities.sendBill(bill);
        logger.info("Bill sent to customer {}", customer.getCustomerID());
        return Optional.of(confirmation);
      } catch (InvalidChargeAmountException e) {
        logger.error("Unable to bill customer");
        throw Workflow.wrap(e);
      }
    } else {
      logger.info("Order was not fulfilled. Not billing the customer.");
    }

    return Optional.empty();

  }

  @Override
  public void fulfillOrderSignal(boolean bool) {
    this.fulfilled = bool;
  }
}
