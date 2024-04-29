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

    PizzaWorkflow workflow = Workflow.newExternalWorkflowStub(PizzaWorkflow.class, workflowID);

    try {
      activities.makePizzas(order);
      activities.deliverPizzas(order);
      workflow.fulfillOrderSignal(true);
      return "order fulfilled";
    } catch (Exception e) {
      workflow.fulfillOrderSignal(false);
      return "order not fulfilled";
    }

  }

}
