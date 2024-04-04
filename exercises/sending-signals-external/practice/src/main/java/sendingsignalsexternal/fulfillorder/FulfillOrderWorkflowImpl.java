package sendingsignalsexternal.fulfillorder;

import sendingsignalsexternal.model.PizzaOrder;
import sendingsignalsexternal.orderpizza.PizzaWorkflow;
import sendingsignalsexternal.Constants;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.time.Duration;
import org.slf4j.Logger;

public class FulfillOrderWorkflowImpl implements FulfillOrderWorkflow {

  public static final Logger logger = Workflow.getLogger(FulfillOrderWorkflowImpl.class);

  ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build();

  private final FulfillOrderActivities activities = Workflow.newActivityStub(FulfillOrderActivities.class, options);

  public String fulfillOrder(PizzaOrder order, String workflowID) {

    // TODO: PART C: Create the Workflow Stub for PizzaOrderWorkflow

    try {
      activities.makePizzas(order);
      activities.deliverPizzas(order);
      
      // TODO: PART D: Signal the workflow that the order was fulfilled successfully
      return "order fulfilled";
    } catch (Exception e) {
      // TODO: PART D: Signal the workflow that the order failed to be fulfilled
      return "order not fulfilled";
    }

  }

}
