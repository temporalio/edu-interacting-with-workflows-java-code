package queryingworkflows;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface QueryingWorkflowsWorkflow {

  @WorkflowMethod
  String workflow(String input);

  // Define the workflow fulfillOrderSignal signal method. This method is executed when the workflow
  // receives a signal.
  @SignalMethod
  void fulfillOrderSignal(boolean bool);

  // Workflow query method. Used to return our greeting as a query value
  @QueryMethod
  String queryState();

}
