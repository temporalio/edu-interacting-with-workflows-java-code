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
    if (this.fulfilled == true){
      activityResult = activities.activity(input);
      logger.info("Signal workflow completed with result: {}", activityResult);
    }

    return activityResult;
  }

  @Override
  public void fulfillOrderSignal(boolean bool){
    this.fulfilled = bool;
  }
}
