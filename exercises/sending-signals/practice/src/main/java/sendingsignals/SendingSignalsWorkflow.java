package sendingsignals;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SendingSignalsWorkflow {

  @WorkflowMethod
  String workflow(String input);

  // TODO Part A: Define a new void `method` named `FulfillOrderSignal`.
  // Annotate the method with `@SignalMethod`. It should take a single boolean
  // that signifies if the order was fulfilled.

}
