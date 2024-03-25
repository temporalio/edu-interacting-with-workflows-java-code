package sendingsignals;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SendingSignalsActivities {

  String activity(String input);

}
