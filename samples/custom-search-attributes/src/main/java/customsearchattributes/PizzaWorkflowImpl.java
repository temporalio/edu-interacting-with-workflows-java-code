package customsearchattributes;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.failure.ApplicationFailure;
import io.temporal.common.SearchAttributeKey;

import customsearchattributes.model.Address;
import customsearchattributes.model.Bill;
import customsearchattributes.model.Customer;
import customsearchattributes.model.Distance;
import customsearchattributes.model.OrderConfirmation;
import customsearchattributes.model.Pizza;
import customsearchattributes.model.PizzaOrder;
import customsearchattributes.exceptions.InvalidChargeAmountException;
import customsearchattributes.exceptions.OutOfServiceAreaException;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

public class PizzaWorkflowImpl implements PizzaWorkflow {

  public static final Logger logger = Workflow.getLogger(PizzaWorkflowImpl.class);

  ActivityOptions options =
      ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

  private final PizzaActivities activities =
      Workflow.newActivityStub(PizzaActivities.class, options);

  @Override
  public OrderConfirmation orderPizza(PizzaOrder order) {

    String orderNumber = order.getOrderNumber();
    Customer customer = order.getCustomer();
    List<Pizza> items = order.getItems();
    boolean isDelivery = order.isDelivery();
    Address address = order.getAddress();

    final SearchAttributeKey<Boolean> IS_ORDER_FAILED = SearchAttributeKey.forBoolean("isOrderFailed");

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

      Workflow.upsertTypedSearchAttributes(IS_ORDER_FAILED.valueSet(true));

      throw new NullPointerException("Unable to get distance");
    }

    if (isDelivery && (distance.getKilometers() > 25)) {
      logger.error("Customer lives outside the service area");
      Workflow.upsertTypedSearchAttributes(IS_ORDER_FAILED.valueSet(true));
      throw ApplicationFailure.newFailure("Customer lives outside the service area",
          OutOfServiceAreaException.class.getName());
    }

    logger.info("distance is {}", distance.getKilometers());

    // Use a short Timer duration here to simulate the passage of time
    // while avoiding delaying the exercise.
    Workflow.sleep(Duration.ofSeconds(3));

    Bill bill = new Bill(customer.getCustomerID(), orderNumber, "Pizza", totalPrice);

    OrderConfirmation confirmation;
    try {
      confirmation = activities.sendBill(bill);
    } catch (InvalidChargeAmountException e) {
      logger.error("Unable to bill customer");
      Workflow.upsertTypedSearchAttributes(IS_ORDER_FAILED.valueSet(true));
      throw Workflow.wrap(new InvalidChargeAmountException("Unable to bill customer"));
    }

    Workflow.upsertTypedSearchAttributes(IS_ORDER_FAILED.valueSet(false));
    return confirmation;
  }
}
