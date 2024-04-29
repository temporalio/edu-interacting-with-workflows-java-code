package sendingsignalsexternal.orderpizza;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.SignalMethod;
import sendingsignalsexternal.model.PizzaOrder;
import sendingsignalsexternal.model.OrderConfirmation;

@WorkflowInterface
public interface PizzaWorkflow {

  @WorkflowMethod
  OrderConfirmation orderPizza(PizzaOrder order);

  // TODO: PART A: Define the Signal method and annotate it appropriately here.

}
