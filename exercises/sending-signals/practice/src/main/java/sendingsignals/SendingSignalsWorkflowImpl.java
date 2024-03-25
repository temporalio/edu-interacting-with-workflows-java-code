package sendingsignals;

import java.time.Duration;
import org.slf4j.Logger;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;


public class SendingSignalsWorkflowImpl implements SendingSignalsWorkflow {

  public static final Logger logger = Workflow.getLogger(SendingSignalsWorkflowImpl.class);

  private boolean fulfilled = false;

  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofSeconds(5))
          .build();

  private final SendingSignalsActivities activities =
      Workflow.newActivityStub(SendingSignalsActivities.class, options);

  @Override
  public String workflow(String input) {
    
    logger.info("Signal workflow started with input: {}", input);

    Workflow.await(() -> this.fulfilled);

    String activityResult = "";

    // TODO PART B: Wrap the execution of the below Activity in an if statement
    // that checks if `fulfilled` is true or not. Also add a log statement 
    // stating that the Workflow is complete and the result.
    activityResult = activities.activity(input);
    

    return activityResult;
  }

  // TODO Part A: Implement a new void `method` named `FulfillOrderSignal`.
  // Annotate the method with `@SignalMethod`. It should take a single boolean
  // that signifies if the order was fulfilled. It should set the instance variable
  // `fulfilled` the value that is received as a paraemeter.
  
}
