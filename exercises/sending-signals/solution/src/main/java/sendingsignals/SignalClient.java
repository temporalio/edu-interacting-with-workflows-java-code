package sendingsignals;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;


public class SignalClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    SendingSignalsWorkflow workflow = client.newWorkflowStub(SendingSignalsWorkflow.class, "signals-workflow");

    workflow.fulfillOrderSignal(true);
    
    System.exit(0);
  }
}
