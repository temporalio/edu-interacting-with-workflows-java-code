package queryingworkflows;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface QueryingWorkflowsActivities {

  String activity(String input);

}
