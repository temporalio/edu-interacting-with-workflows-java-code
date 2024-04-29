package queryingworkflows;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import queryingworkflows.orderpizza.PizzaWorkflow;


public class QueryClient {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    PizzaWorkflow workflow = client.newWorkflowStub(PizzaWorkflow.class, "pizza-workflow-order-XD001");

    System.out.println(workflow.orderStatus());

    System.exit(0);
  }
}