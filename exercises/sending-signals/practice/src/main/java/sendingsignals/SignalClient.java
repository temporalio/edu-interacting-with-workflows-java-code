package sendingsignals;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;


public class SignalClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);


    // TODO: PART C - Update the Workflow ID `"CHANGE-ME"` in the `newWorkflowStub`
    // call to the Workflow ID of the Workflow. 
    // HINT: Check `Starter.java` to see what the Workflow ID was set to.
    SendingSignalsWorkflow workflow = client.newWorkflowStub(SendingSignalsWorkflow.class, "CHANGE-ME");

    // TODO: PART C - call the `fulfillOrderSignal` method with the value `true` using the `workflow` object.
    
    System.exit(0);
  }
}
