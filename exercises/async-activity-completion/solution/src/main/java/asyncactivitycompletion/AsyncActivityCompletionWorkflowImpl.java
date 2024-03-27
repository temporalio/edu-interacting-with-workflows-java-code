package asyncactivitycompletion;

import java.time.Duration;

import org.slf4j.Logger;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;


public class AsyncActivityCompletionWorkflowImpl implements AsyncActivityCompletionWorkflow {

  public static final Logger logger = Workflow.getLogger(AsyncActivityCompletionWorkflowImpl.class);

  private boolean fulfilled = false;
  private String currentState;

  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofSeconds(5))
          .build();

  private final AsyncActivityCompletionActivities activities =
      Workflow.newActivityStub(AsyncActivityCompletionActivities.class, options);

  @Override
  public String workflow(String input) {
    
    logger.info("Async Activity Workflow started with input: {}", input);
   
    
    String activityResult = activities.activity(input);

    logger.info("Async Activity Workflow completed with result: {}", activityResult);

    return activityResult;
  }
}
