package asyncactivitycompletion;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface AsyncActivityCompletionActivities {

  String activity(String input);

}
