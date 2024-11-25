## Exercise 4: Asynchronous Activity Completion

During this exercise, you will:

- Retrieve a task token from your Activity execution
- Set the `doNotCompleteOnReturn()` context to indicate that the Activity is waiting for an external completion
- Use another Temporal Client to communicate the result of the asynchronous Activity back to the Workflow

Make your changes to the code in the `practice` subdirectory (look for `TODO` comments that will guide you to where you should make changes to the code). If you need a hint or want to verify your changes, look at the complete version in the `solution` subdirectory.

### Prerequisites
This exercises uses the microservice from Temporal 102. **If you are in the GitPod 
environment, this microservice is already running.** If you are _not_ using 
GitPod, you can start the microservice from a separate terminal by changing to
the `utilities/microservice` directory and using the following command:

```bash
mvn exec:java -Dexec.mainClass="translationapi.Microservice"
```

### GitPod Environment Shortcuts

If you are executing the exercises in the provided GitPod environment, you
can take advantage of certain aliases to aid in navigation and execution of
the code.

| Command           | Action                                                                                                                                                       |
| :---------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ex4`             | Change to Exercise 4 Practice Directory                                                                                                                      |
| `ex4s`            | Change to Exercise 4 Solution Directory                                                                                                                      |
| `ex4w`            | Execute the Exercise 4 Worker. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)                               |
| `ex4st NAME LANGUAGE_CODE`           | Execute the Exercise 4 Starter, passing in your name and a valid lanuage code. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)                              |
| `ex4c TASK_TOKEN TRANSLATION` | Complete the Exercise 4 Activity, passing in the Task Token and verified translation. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`) |

## Part A: Retrieving the Task Token

1. Open the `TranslationActivitiesImpl.java` file in the `src/main/java/asyncactivitycompletion` subdirectory
1. In the `translateTerm()` method, add the line `ActivityExecutionContext context = Activity.getExecutionContext();` to get the current Execution Context of the Activity
1. Add a call to `getTaskToken()` from the `context` object above and store it in a `byte []` named `taskToken`
1. Uncomment the line below to convert the `taskToken` byte array to Base64
1. Log the value of the `encoded` variable, the Base64-encoded Task Token created by the previous step, at `info` level using the `logger` object
1. Save the file

## Part B: Set Your Activity to `doNotCompleteOnReturn()`

1. Continue editing the same Activity definition in the `TranslationActivitiesImpl.java` file.
   1. Add a call to the `doNotCompleteOnReturn();` method at the end of the `translateTerm()` method using the `context` object from Part A. This notifies Temporal that the Activity should not be completed on return and will be completed asynchronously.
   1. Save the file.
1. Open the `TranslationWorkflowImpl.java` file in the `src/main/java/asyncactivitycompletion` subdirectory.
   1. Observe that the Workflow's `StartToCloseTimeout` has been lengthened to `300` seconds for this exercise. Activities can still time out if they are running in the background.
      1. If you don't do this and your Activity retries due to a timeout, the Task Token will be reset.
   1. Close this file without making any changes.

**Note:** In practice, it is recommended to use Heartbeats for longer running 
Activities. While this exercise doesn't include them, it is a good idea to
include them in Activities that will complete Asynchronously.

## Part C: Configure a Client to send CompleteActivity

1. Open the `VerifyAndCompleteTranslation.java` file in the `src/main/java/asyncactivitycompletion` subdirectory.
1. The first thing you'll need to do is add some way of supplying the `taskToken` and translated text specific to the Activity you are trying to complete at runtime. In a production system, you might store and retrieve the token from a database, but for now, you can configure this Client to accept it as a command line argument. Both the `taskToken` and `translation` can be found in the logs of the Worker.
   1. Read in the token from the command line `args[0]` and decode the base 64, storing it in a `byte[]`. Hint, invert the call in `TranslationActivitiesImpl.java`
   1. The translated term is provided as `args[1]`. Store this in a variable named `result`.
1. Add a call to the `complete();` method using the `activityCompletionClient`. This call should provide the task token and result of the Activity. This notifies Temporal that the Activity should not be completed on return and will be completed asynchronously.
   1. The result has already been instantiated into a `TranslationActivityOutput` object for you.
1. Save the file.

## Part D: Running the Workflow

At this point, you can run your Workflow. As with the Signal Exercise, the Workflow will not return on its own -- in this case, because your Activity is set to complete asynchronously, and will wait to receive `complete()`.

1. In one terminal, navigate to the `practice` subdirectory
   1. If you're in the GitPod environment you can instead run `ex4`
   1. Compile the code using `mvn clean compile`
   1. Run the worker using `mvn exec:java -Dexec.mainClass='asyncactivitycompletion.TranslationWorker'`.
      1. If you're in the GitPod environment you can instead run `ex4w`
1. In another terminal, navigate to the `practice` subdirectory
   1. If you're in the GitPod environment you can instead run `ex4`
   1. Invoke the Workflow using `mvn exec:java -Dexec.mainClass='asyncactivitycompletion.Starter' -Dexec.args="Mason de"`
      If you're in the GitPod environment you can instead run `ex4st Mason de`, replacing the name with yours
1. Navigate back to the Worker terminal. Your work will produce some log messages, eventually including your encoded Task Token:

```bash
10:28:40.579 INFO  - sayHelloGoodbye Workflow Invoked with input name: Mason language code: de
10:28:40.614 INFO  - translateTerm Activity received input: asyncactivitycompletion.model.TranslationActivityInput@394250e6
10:28:40.614 INFO  - TASK TOKEN: CiQ1NzVkNTNlYi1lM2UyLTRmNmEtODFjMy04ZmY0NmJiYjJjOWYSFHRyYW5zbGF0aW9uLXdvcmtmbG93GiQ0OWQ5NjgyOC1iYmJkLTQ5MjMtOTE4Mi00MWY2YmFlNjI4YzEgBSgBMiRlNGJmZmJhMC1jNGJhLTM1MDgtYThkYS01MjgwYjNjMzVkZmJCDVRyYW5zbGF0ZVRlcm1KCAgBEJuAQBgB
10:28:40.614 INFO  - [ACTIVITY INVOKED] translateTerm invoked with input term: hello language code: de
10:28:40.642 INFO  - Translation Service returned: Hallo
```

## Part E: Completing the Activity from Another Client

1. You can now use this token to send a `complete()` call from another client. In another terminal, navigate to the `practice` subdirectory
   1. If you're in the GitPod environment you can instead run `ex4`
   1. Run the command `mvn exec:java -Dexec.mainClass='asyncactivitycompletion.VerifyAndCompleteTranslation' -Dexec.args="TASK_TOKEN TRANSLATION"` with your Task Token replacing `TASK_TOKEN` with your Task Token to complete the Activity and replacing `TRANSLATION` with the results from the translation service
      1. If you're in the GitPod environment you can instead run `ex4c TASK_TOKEN TRANSLATION` replacing `TASK_TOKEN` with your Task Token to complete the Activity and replacing `TRANSLATION` with the results from the translation service
   1. This will cause your Activity to return and your Workflow to successfully complete. The terminal running your Worker process should now show
   ```
   12:07:43.689 INFO  - Workflow Completed
   ```

### This is the end of the exercise.
