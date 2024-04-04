package queryingworkflows;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;


public class SignalClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    QueryingWorkflowsWorkflow workflow = client.newWorkflowStub(QueryingWorkflowsWorkflow.class, "query-workflow");

    workflow.fulfillOrderSignal(true);
    
    System.exit(0);
  }
}
