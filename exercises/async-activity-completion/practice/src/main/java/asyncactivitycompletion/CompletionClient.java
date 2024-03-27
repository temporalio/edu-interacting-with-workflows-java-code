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

    // TODO Part C: Read in the token from the command line `args[0]` and decode
    // the base 64, storing it in a `byte[]`. Hint, invert the call in `AsyncActivityCompletionActivitiesImpl.java`
    
    ActivityCompletionClient activityCompletionClient = client.newActivityCompletionClient();

    // TODO Part C: Use the `activityCompletionClient` object above to `complete()`
    // the Activity, passing in the Task Token and the result.
    
    System.exit(0);
  }
}
