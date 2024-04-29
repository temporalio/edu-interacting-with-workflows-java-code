package sendingsignalsclient;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import sendingsignalsclient.orderpizza.PizzaWorkflow;


public class SignalClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    PizzaWorkflow workflow = client.newWorkflowStub(PizzaWorkflow.class, "pizza-workflow-order-XD001");

    workflow.fulfillOrderSignal(true);

    System.exit(0);
  }
}