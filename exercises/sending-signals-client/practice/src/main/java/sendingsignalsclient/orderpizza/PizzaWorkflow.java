package sendingsignalsclient.orderpizza;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import io.temporal.workflow.SignalMethod;
import sendingsignalsclient.model.PizzaOrder;
import sendingsignalsclient.model.OrderConfirmation;
import java.util.Optional;

@WorkflowInterface
public interface PizzaWorkflow {

  @WorkflowMethod
  Optional<OrderConfirmation> orderPizza(PizzaOrder order);

  @SignalMethod
  void fulfillOrderSignal(boolean bool);

}
