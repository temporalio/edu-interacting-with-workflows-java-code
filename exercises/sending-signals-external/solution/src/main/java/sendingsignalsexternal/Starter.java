package sendingsignalsexternal;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import java.util.concurrent.CompletableFuture;
import io.temporal.serviceclient.WorkflowServiceStubs;

import sendingsignalsexternal.model.PizzaOrder;
import sendingsignalsexternal.model.Pizza;
import sendingsignalsexternal.model.Customer;
import sendingsignalsexternal.model.OrderConfirmation;
import sendingsignalsexternal.model.Address;
import sendingsignalsexternal.fulfillorder.FulfillOrderWorkflow;
import sendingsignalsexternal.orderpizza.PizzaWorkflow;

import java.util.Arrays;
import java.util.List;

public class Starter {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    PizzaOrder order = createPizzaOrder();

    // Setup the Pizza Order Workflow Client
    String pizzaWorkflowID = String.format("pizza-workflow-order-%s", order.getOrderNumber());

    WorkflowOptions pizzaWorkflowOptions = WorkflowOptions.newBuilder()
        .setWorkflowId(pizzaWorkflowID)
        .setTaskQueue(Constants.TASK_QUEUE_NAME)
        .build();

    PizzaWorkflow pizzaWorkflow = client.newWorkflowStub(PizzaWorkflow.class, pizzaWorkflowOptions);

    // Setup the Fulfill Order Workflow Client

    String fulfillWorkflowID = String.format("signal-fulfilled-order-%s", order.getOrderNumber());

    WorkflowOptions fulfillWorkflowOptions = WorkflowOptions.newBuilder()
        .setWorkflowId(fulfillWorkflowID)
        .setTaskQueue(Constants.TASK_QUEUE_NAME)
        .build();

    FulfillOrderWorkflow orderWorkflow = client.newWorkflowStub(FulfillOrderWorkflow.class, fulfillWorkflowOptions);

    CompletableFuture<OrderConfirmation> orderConfirmation = WorkflowClient.execute(pizzaWorkflow::orderPizza, order);

    CompletableFuture<String> fulfillOrderResult = WorkflowClient.execute(orderWorkflow::fulfillOrder, order,
        pizzaWorkflowID);


    System.out.printf("Workflow result: %s\n", orderConfirmation.get());
    System.exit(0);
  }

  private static PizzaOrder createPizzaOrder() {
    Customer customer = new Customer(8675309, "Lisa Anderson", "lisa@example.com", "555-555-0000");
    Address address = new Address("742 Evergreen Terrace", "Apartment 221B", "Albuquerque", "NM", "87101");
    Pizza pizza1 = new Pizza("Large, with mushrooms and onions", 1500);
    Pizza pizza2 = new Pizza("Small, with pepperoni", 1200);
    Pizza pizza3 = new Pizza("Medium, with extra cheese", 1300);

    List<Pizza> orderList = Arrays.asList(pizza1, pizza2, pizza3);

    PizzaOrder order = new PizzaOrder("XD001", customer, orderList, true, address);

    return order;
  }
}
