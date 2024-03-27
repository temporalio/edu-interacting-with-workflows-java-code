package asyncactivitycompletion;

import java.util.concurrent.CompletableFuture;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;


public class Starter {
  public static void main(String[] args) throws Exception {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    WorkflowOptions options = WorkflowOptions.newBuilder()
        .setWorkflowId("async-complete-workflow")
        .setTaskQueue("async-complete")
        .build();

    AsyncActivityCompletionWorkflow workflow = client.newWorkflowStub(AsyncActivityCompletionWorkflow.class, options);

    CompletableFuture<String> result = WorkflowClient.execute(workflow::workflow, "Plain text input");

    System.out.printf("Workflow result: %s\n", result.get());
    System.exit(0);
  }
}
