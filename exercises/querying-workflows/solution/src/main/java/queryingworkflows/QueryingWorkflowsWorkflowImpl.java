package queryingworkflows;

import java.time.Duration;

import javax.management.Query;

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
    
    currentState = "started";
    logger.info("Signal workflow started with input: {}", input);
   
    currentState = "waiting for signal";
    Workflow.await(() -> this.fulfilled);

    String activityResult = "";
    if (this.fulfilled == true){
      activityResult = activities.activity(input);
      logger.info("Signal workflow completed with result: {}", activityResult);
    }

    currentState = "Workflow completed";

    return activityResult;
  }

  @Override
  public void fulfillOrderSignal(boolean bool){
    this.fulfilled = bool;
  }

  @Override
  public String queryState(){
    return this.currentState;
  }
}
