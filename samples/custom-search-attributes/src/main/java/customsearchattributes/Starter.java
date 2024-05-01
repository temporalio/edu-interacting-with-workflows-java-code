package customsearchattributes;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.common.SearchAttributes;

import customsearchattributes.model.PizzaOrder;
import customsearchattributes.model.Pizza;
import customsearchattributes.model.Customer;
import customsearchattributes.model.OrderConfirmation;
import customsearchattributes.model.Address;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Starter {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    PizzaOrder order = createPizzaOrder();

    String workflowID = String.format("pizza-workflow-order-%s", order.getOrderNumber());

    WorkflowOptions options = WorkflowOptions.newBuilder()
        .setWorkflowId(workflowID)
        .setTaskQueue(Constants.TASK_QUEUE_NAME)
        .setTypedSearchAttributes(generateSearchAttributes())
        .build();

    PizzaWorkflow workflow = client.newWorkflowStub(PizzaWorkflow.class, options);

    OrderConfirmation orderConfirmation = workflow.orderPizza(order);

    System.out.printf("Workflow result: %s\n", orderConfirmation);
    System.exit(0);
  }

  private static PizzaOrder createPizzaOrder() {
    Customer customer = new Customer(8675309, "Lisa Anderson", "lisa@example.com", "555-555-0000");
    Address address =
        new Address("742 Evergreen Terrace", "Apartment 221B", "Albuquerque", "NM", "87101");
    Pizza pizza1 = new Pizza("Large, with mushrooms and onions", 1500);
    Pizza pizza2 = new Pizza("Small, with pepperoni", 1200);
    Pizza pizza3 = new Pizza("Medium, with extra cheese", 1300);

    List<Pizza> orderList = Arrays.asList(pizza1, pizza2, pizza3);

    PizzaOrder order = new PizzaOrder("XD001", customer, orderList, true, address);

    return order;
  }

  private static SearchAttributes generateSearchAttributes(){
    return SearchAttributes.newBuilder().set(Constants.IS_ORDER_FAILED, false).build();
  }
}
