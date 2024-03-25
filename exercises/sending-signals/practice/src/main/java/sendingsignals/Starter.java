package sendingsignals;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;


public class Starter {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    WorkflowOptions options = WorkflowOptions.newBuilder()
        .setWorkflowId("signals-workflow")
        .setTaskQueue("signals")
        .build();

    SendingSignalsWorkflow workflow = client.newWorkflowStub(SendingSignalsWorkflow.class, options);

    String result = workflow.workflow("Plain text input");

    System.out.printf("Workflow result: %s\n", result);
    System.exit(0);
  }
}
