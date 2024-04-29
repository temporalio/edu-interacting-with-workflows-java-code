package asyncactivitycompletion;

import io.temporal.activity.ActivityInterface;
import asyncactivitycompletion.model.TranslationActivityInput;
import asyncactivitycompletion.model.TranslationActivityOutput;

@ActivityInterface
public interface TranslationActivities {

  TranslationActivityOutput translateTerm(TranslationActivityInput input);

}
