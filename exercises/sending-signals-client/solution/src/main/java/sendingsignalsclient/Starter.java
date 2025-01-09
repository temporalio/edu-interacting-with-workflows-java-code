package sendingsignalsclient;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import java.util.concurrent.CompletableFuture;
import io.temporal.serviceclient.WorkflowServiceStubs;

import sendingsignalsclient.model.PizzaOrder;
import sendingsignalsclient.model.Pizza;
import sendingsignalsclient.model.Customer;
import sendingsignalsclient.model.OrderConfirmation;
import sendingsignalsclient.model.Address;
import sendingsignalsclient.orderpizza.PizzaWorkflow;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    Optional<OrderConfirmation> orderConfirmation = pizzaWorkflow.orderPizza(order);
    // CompletableFuture<Optional<OrderConfirmation>> orderConfirmationFuture = WorkflowClient.execute(pizzaWorkflow::orderPizza, order);
    // Optional<OrderConfirmation> orderConfirmation = orderConfirmationFuture.get();


    System.out.printf("Workflow result: %s\n", orderConfirmation);
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
