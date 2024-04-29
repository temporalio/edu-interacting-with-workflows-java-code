package sendingsignalsexternal.fulfillorder;

import sendingsignalsexternal.model.PizzaOrder;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface FulfillOrderWorkflow {

  @WorkflowMethod
  String fulfillOrder(PizzaOrder pizza, String workflowID);

}
