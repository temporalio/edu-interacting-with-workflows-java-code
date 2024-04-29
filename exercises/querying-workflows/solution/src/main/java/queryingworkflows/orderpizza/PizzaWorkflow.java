package queryingworkflows.orderpizza;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.QueryMethod;
import queryingworkflows.model.PizzaOrder;
import queryingworkflows.model.OrderConfirmation;

@WorkflowInterface
public interface PizzaWorkflow {

  @WorkflowMethod
  OrderConfirmation orderPizza(PizzaOrder order);

  @SignalMethod
  void fulfillOrderSignal(boolean bool);

  @QueryMethod
  String orderStatus();

}
