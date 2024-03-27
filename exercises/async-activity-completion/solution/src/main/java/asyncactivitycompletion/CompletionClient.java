package asyncactivitycompletion;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.client.ActivityCompletionClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletionClient {
  public static void main(String[] args) throws ExecutionException, InterruptedException {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);
    
    String result = "Asynchronously completed";
    byte[] taskToken = Base64.getDecoder().decode(args[0]);

    ActivityCompletionClient activityCompletionClient = client.newActivityCompletionClient();
    
    activityCompletionClient.complete(taskToken, result);
    
    System.exit(0);
  }
}
