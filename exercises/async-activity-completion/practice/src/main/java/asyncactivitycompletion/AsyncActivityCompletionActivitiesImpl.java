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


    // TODO PART A:
    // * Add the code to get the Activity execution context
    // * Add the code to get the Task Token from the context
    // * Uncomment the code to encode the Task Token in Base64
    // * Log the task token at info level. Hint, you will need to convert the byte[] to a new String

    //byte[] encoded = Base64.getEncoder().encode(taskToken);
    

    // TODO Part B:  Add a call to the `doNotCompleteOnReturn();` method using 
    //the `context` object from Part A. This notifies Temporal that the Activity
    //should not be completed on return and will be completed asynchronously.

    return "This return value will be ignored.";
  }

}
