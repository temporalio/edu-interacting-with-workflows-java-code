package asyncactivitycompletion;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface AsyncActivityCompletionWorkflow {

  @WorkflowMethod
  String workflow(String input);

}
