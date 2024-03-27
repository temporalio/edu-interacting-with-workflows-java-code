package asyncactivitycompletion;


import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;


import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;


public class AsyncActivityCompletionActivitiesImpl implements AsyncActivityCompletionActivities {

  private static final Logger logger = LoggerFactory.getLogger(AsyncActivityCompletionActivitiesImpl.class);

  private final ActivityCompletionClient completionClient;

  AsyncActivityCompletionActivitiesImpl(ActivityCompletionClient completionClient) {
    this.completionClient = completionClient;
  }

  @Override
  public String activity(String input) {
    logger.info("Activity received input: {}", input);

    // Get the activity execution context
    ActivityExecutionContext context = Activity.getExecutionContext();

    // Set a correlation token that can be used to complete the activity asynchronously
    byte[] taskToken = context.getTaskToken();

    logger.info("Task token: " + new String(taskToken));

    /*
    * For the example we will use a {@link java.util.concurrent.ForkJoinPool} to execute our
    * activity. In real-life applications this could be any service. The composeGreetingAsync
    * method is the one that will actually complete workflow action execution.
    */
    ForkJoinPool.commonPool().execute(() -> completeActivity(taskToken, input));
    context.doNotCompleteOnReturn();

    // Since we have set doNotCompleteOnReturn(), the workflow action method return value is
    // ignored.
    return "ignored";
  }

  private void completeActivity(byte[] taskToken, String input){
    String result = "Received: " + input;

    // Complete our workflow activity using ActivityCompletionClient
    completionClient.complete(taskToken, result);
  }

}
