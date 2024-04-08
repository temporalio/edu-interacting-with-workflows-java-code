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
    
    
    byte[] taskToken = Base64.getDecoder().decode(args[0]);

    // Pass in the translated text as a command line argument and pretend to "verify"
    // the results
    String result = args[1];

    ActivityCompletionClient activityCompletionClient = client.newActivityCompletionClient();
    
    activityCompletionClient.complete(taskToken, new TranslationActivityOutput(result));
    
    System.exit(0);
  }
}