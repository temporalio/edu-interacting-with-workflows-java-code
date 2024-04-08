package asyncactivitycompletion;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import asyncactivitycompletion.model.TranslationWorkflowInput;
import asyncactivitycompletion.model.TranslationWorkflowOutput;

@WorkflowInterface
public interface TranslationWorkflow {

  @WorkflowMethod
  TranslationWorkflowOutput sayHelloGoodbye(TranslationWorkflowInput input);

}
