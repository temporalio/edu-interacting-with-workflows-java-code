package asyncactivitycompletion;


import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;


public class AsyncActivityCompletionActivitiesImpl implements AsyncActivityCompletionActivities {

  private static final Logger logger = LoggerFactory.getLogger(AsyncActivityCompletionActivitiesImpl.class);

  @Override
  public String activity(String input) {
    logger.info("Activity received input: {}", input);

    // Get the activity execution context
    ActivityExecutionContext context = Activity.getExecutionContext();

    // Set a correlation token that can be used to complete the activity asynchronously
    byte[] taskToken = context.getTaskToken();

    byte[] encoded = Base64.getEncoder().encode(taskToken);
    logger.info(new String(encoded));

    context.doNotCompleteOnReturn();

    // Since we have set doNotCompleteOnReturn(), the workflow action method return value is
    // ignored.
    return "This return value will be ignored.";
  }

}
