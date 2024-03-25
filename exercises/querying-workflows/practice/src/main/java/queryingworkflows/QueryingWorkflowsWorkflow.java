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

  // TODO Part A: Define a new void `method` named `queryState`.
  // Annotate the method with `@QueryMethod`. It should return a String, which
  // will be the state of the Workflow. It takes no parameters.

}
