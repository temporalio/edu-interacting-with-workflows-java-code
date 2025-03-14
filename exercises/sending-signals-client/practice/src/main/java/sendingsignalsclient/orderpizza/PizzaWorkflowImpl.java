package sendingsignalsclient.orderpizza;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.failure.ApplicationFailure;

import sendingsignalsclient.model.Address;
import sendingsignalsclient.model.Bill;
import sendingsignalsclient.model.Customer;
import sendingsignalsclient.model.Distance;
import sendingsignalsclient.model.OrderConfirmation;
import sendingsignalsclient.model.Pizza;
import sendingsignalsclient.model.PizzaOrder;
import sendingsignalsclient.exceptions.InvalidChargeAmountException;
import sendingsignalsclient.exceptions.OutOfServiceAreaException;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;

public class PizzaWorkflowImpl implements PizzaWorkflow {

  public static final Logger logger = Workflow.getLogger(PizzaWorkflowImpl.class);

  private boolean fulfilled;
  private boolean signalProcessed;

  ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

  private final PizzaActivities activities = Workflow.newActivityStub(PizzaActivities.class, options);

  @Override
  public OrderConfirmation orderPizza(PizzaOrder order) {

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

    Workflow.await(Duration.ofSeconds(10),() -> this.signalProcessed);

    OrderConfirmation confirmation;

    if (this.fulfilled) {
      Bill bill = new Bill(customer.getCustomerID(), orderNumber, "Pizza", totalPrice);

      try {
        confirmation = activities.sendBill(bill);
        logger.info("Bill sent to customer {}", customer.getCustomerID());
      } catch (InvalidChargeAmountException e) {
        logger.error("Unable to bill customer");
        throw Workflow.wrap(e);
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
    this.signalProcessed = true;
  }
}
