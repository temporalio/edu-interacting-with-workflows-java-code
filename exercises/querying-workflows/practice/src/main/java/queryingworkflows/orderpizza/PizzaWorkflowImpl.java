package queryingworkflows.orderpizza;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.failure.ApplicationFailure;

import queryingworkflows.model.Address;
import queryingworkflows.model.Bill;
import queryingworkflows.model.Customer;
import queryingworkflows.model.Distance;
import queryingworkflows.model.OrderConfirmation;
import queryingworkflows.model.Pizza;
import queryingworkflows.model.PizzaOrder;
import queryingworkflows.exceptions.InvalidChargeAmountException;
import queryingworkflows.exceptions.OutOfServiceAreaException;

import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;

public class PizzaWorkflowImpl implements PizzaWorkflow {

  public static final Logger logger = Workflow.getLogger(PizzaWorkflowImpl.class);

  private boolean fulfilled;
  private String status;

  ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

  private final PizzaActivities activities = Workflow.newActivityStub(PizzaActivities.class, options);

  @Override
  public OrderConfirmation orderPizza(PizzaOrder order) {

    // TODO: PART A: Assign the value `Started` to `status`
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

    // TODO: PART A: Assign the value `Out for delivery` to `status`

    Workflow.await(() -> this.fulfilled);

    OrderConfirmation confirmation;

    if (this.fulfilled) {
      Bill bill = new Bill(customer.getCustomerID(), orderNumber, "Pizza", totalPrice);

      try {
        confirmation = activities.sendBill(bill);
        logger.info("Bill sent to customer {}", customer.getCustomerID());
        // TODO: PART A: Assign the value `Order complete` to `status`
      } catch (InvalidChargeAmountException e) {
        status = "Unable to bill customer";
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

  // TODO: PART A: Implement the `orderStatus` query method. It should return the
  // value of `status`.
}