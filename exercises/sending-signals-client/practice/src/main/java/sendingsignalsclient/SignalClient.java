package sendingsignalsclient;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import sendingsignalsclient.orderpizza.PizzaWorkflow;


public class SignalClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    // TODO: PART A: Set Workflow ID
    PizzaWorkflow workflow = client.newWorkflowStub(PizzaWorkflow.class, "CHANGE_ME");

    // TODO: PART B: Call fulfillOrderSignal, passing `true`

    System.exit(0);
  }
}