package asyncactivitycompletion;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.client.ActivityCompletionClient;

import asyncactivitycompletion.model.TranslationActivityOutput;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class VerifyAndCompleteTranslation {
  public static void main(String[] args) throws ExecutionException, InterruptedException {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    WorkflowClient client = WorkflowClient.newInstance(service);
    
    
    // TODO: PART C: Read in the taskToken from args[0] and decode it from Base64

    // TODO: PART C: Get the translated text from args[1] and use it to create a
    // new TranslationActivityOutput object

    ActivityCompletionClient activityCompletionClient = client.newActivityCompletionClient();

    // TODO: PART C: Call the `complete()` method using the `activityCompletionClient` object
    // from above. The call should contain the `taskToken` and the `result`.
    
    System.exit(0);
  }
}