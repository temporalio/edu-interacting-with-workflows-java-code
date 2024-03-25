package queryingworkflows;

import java.time.Duration;
import org.slf4j.Logger;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;


public class QueryingWorkflowsWorkflowImpl implements QueryingWorkflowsWorkflow {

  public static final Logger logger = Workflow.getLogger(QueryingWorkflowsWorkflowImpl.class);

  private boolean fulfilled = false;
  private String currentState;

  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofSeconds(5))
          .build();

  private final QueryingWorkflowsActivities activities =
      Workflow.newActivityStub(QueryingWorkflowsActivities.class, options);

  @Override
  public String workflow(String input) {
    
    // TODO PART A: Set `currentState` to `started`
    
    logger.info("Signal workflow started with input: {}", input);
    
   
    // TODO PART A: Set `currentState` to `waiting for signal`
    Workflow.await(() -> this.fulfilled);

    String activityResult = "";
    if (this.fulfilled == true){
      activityResult = activities.activity(input);
      logger.info("Signal workflow completed with result: {}", activityResult);
    }

    // TODO PART A: Set `currentState` to `Workflow completed`

    return activityResult;
  }

  @Override
  public void fulfillOrderSignal(boolean bool){
    this.fulfilled = bool;
  }

  // TODO PART A:   1. Implement the `queryState` method. This method should 
  // read the instance variable `currentState` and return it.
  
}
