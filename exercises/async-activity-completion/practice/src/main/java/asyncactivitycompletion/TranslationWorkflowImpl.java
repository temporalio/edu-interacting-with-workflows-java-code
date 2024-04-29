package asyncactivitycompletion;

import java.time.Duration;
import org.slf4j.Logger;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import asyncactivitycompletion.model.TranslationActivityInput;
import asyncactivitycompletion.model.TranslationActivityOutput;
import asyncactivitycompletion.model.TranslationWorkflowInput;
import asyncactivitycompletion.model.TranslationWorkflowOutput;

public class TranslationWorkflowImpl implements TranslationWorkflow {

  public static final Logger logger = Workflow.getLogger(TranslationWorkflowImpl.class);

  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofSeconds(300))
          .build();

  private final TranslationActivities activities =
      Workflow.newActivityStub(TranslationActivities.class, options);

  @Override
  public TranslationWorkflowOutput sayHelloGoodbye(TranslationWorkflowInput input) {
    String name = input.getName();
    String languageCode = input.getLanguageCode();

    logger.info("sayHelloGoodbye Workflow Invoked with input name: {} language code: {}", name,
        languageCode);

    logger.debug("Preparing to translate Hello into languageCode: {}", languageCode);
    TranslationActivityInput helloInput = new TranslationActivityInput("hello", languageCode);
    TranslationActivityOutput helloResult = activities.translateTerm(helloInput);
    String helloMessage = helloResult.getTranslation() + ", " + name;

    logger.info("Workflow completed");

    return new TranslationWorkflowOutput(helloMessage);
  }
}
