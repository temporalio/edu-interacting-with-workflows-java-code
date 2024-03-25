package queryingworkflows;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;


public class QueryClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    QueryingWorkflowsWorkflow workflow = client.newWorkflowStub(QueryingWorkflowsWorkflow.class, "query-workflow");

    // TODO PART B: Call the `queryState` method using the `workflow` object and
    // print the result
    
    System.exit(0);
  }
}
