package queryingworkflows;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;


public class QueryClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    QueryingWorkflowsWorkflow workflow = client.newWorkflowStub(QueryingWorkflowsWorkflow.class, "query-workflow");

    String result = workflow.queryState();

    System.out.println("Received Query result. Result: " + result);
    
    System.exit(0);
  }
}
