package asyncactivitycompletion;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletionClient {
  public static void main(String[] args) throws ExecutionException, InterruptedException {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);

    AsyncActivityCompletionWorkflow workflow = client.newWorkflowStub(AsyncActivityCompletionWorkflow.class, "async-complete-workflow");

    /*
     * Here we use {@link io.temporal.client.WorkflowClient} to execute our workflow asynchronously.
     * It gives us back a {@link java.util.concurrent.CompletableFuture}. We can then call its get
     * method to block and wait until a result is available.
     */
    CompletableFuture<String> result = WorkflowClient.execute(workflow::workflow, "Plain text");

    // Wait for workflow execution to complete and display its results.
    System.out.println("Received result" + result.get());
    
    System.exit(0);
  }
}
