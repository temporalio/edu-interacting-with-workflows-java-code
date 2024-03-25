package sendingsignals;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SendingSignalsWorkflow {

  @WorkflowMethod
  String workflow(String input);

  // Define the workflow fulfillOrderSignal signal method. This method is executed when the workflow
  // receives a signal.
  @SignalMethod
  void fulfillOrderSignal(boolean bool);

}
