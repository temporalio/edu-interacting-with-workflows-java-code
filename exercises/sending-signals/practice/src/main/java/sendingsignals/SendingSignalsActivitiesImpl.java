package sendingsignals;


import io.temporal.activity.Activity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendingSignalsActivitiesImpl implements SendingSignalsActivities {

  private static final Logger logger = LoggerFactory.getLogger(SendingSignalsActivitiesImpl.class);

  @Override
  public String activity(String input) {
    logger.info("Activity received input: {}", input);

    return "Received " + input;
  }

}
