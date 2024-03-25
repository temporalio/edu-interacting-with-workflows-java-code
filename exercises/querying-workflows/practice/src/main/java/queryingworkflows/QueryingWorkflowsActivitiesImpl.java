package queryingworkflows;


import io.temporal.activity.Activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueryingWorkflowsActivitiesImpl implements QueryingWorkflowsActivities {

  private static final Logger logger = LoggerFactory.getLogger(QueryingWorkflowsActivitiesImpl.class);

  @Override
  public String activity(String input) {
    logger.info("Activity received input: {}", input);

    return "Received " + input;
  }

}
