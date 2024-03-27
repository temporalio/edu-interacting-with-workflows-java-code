# Exercise 3: Asynchronous Activity Completion

During this exercise, you will:

- Retrieve a task token from your Activity execution
- Set the `doNotCompleteOnReturn()` context to indicate that the Activity is waiting for an external completion.
- Use another Temporal Client to communicate the result of the asynchronous Activity back to the Workflow

Make your changes to the code in the `practice` subdirectory (look for `TODO` comments that will guide you to where you should make changes to the code). If you need a hint or want to verify your changes, look at the complete version in the `solution` subdirectory.

### GitPod Environment Shortcuts

If you are executing the exercises in the provided GitPod environment, you
can take advantage of certain aliases to aid in navigation and execution of
the code.

| Command           | Action                                                                                                                                                       |
| :---------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ex3`             | Change to Exercise 3 Practice Directory                                                                                                                      |
| `ex3s`            | Change to Exercise 3 Solution Directory                                                                                                                      |
| `ex3w`            | Execute the Exercise 3 Worker. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)                               |
| `ex3st`           | Execute the Exercise 3 Starter. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)                              |
| `ex3c TASK_TOKEN` | Complete the Exercise 3 Activity, passing in the Task Token. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`) |

## Part A: Retrieving the Task Token

1. Open the `AsyncActivityCompletionActivitiesImpl.java` file in the `src/main/java/asyncactivitycompletion` subdirectory.
1. In the `activity()` definition, add the line `ActivityExecutionContext context = Activity.getExecutionContext();` to get the current Execution Context of the Activity.
1. Add a call to `getTaskToken()` from the `context` object above and store it in a `byte []` named `taskToken`
1. Uncomment the line below to convert the `taskToken` byte array to Base64.
1. Log the Task Token at `info` level using the `logger` object for later use. You will need to convert this to a new String.
1. Save the file.

## Part B: Set Your Activity to `doNotCompleteOnReturn()`

1. Continue editing the same Activity definition in the `AsyncActivityCompletionImpl.java` file.
   1. Add a call to the `doNotCompleteOnReturn();` method using the `context` object from Part A. This notifies
      Temporal that the Activity should not be completed on return and will be completed asynchronously.
   1. Save the file.
1. Open the `AsyncActivityCompletionWorkflowImpl.java` file in the `src/main/java/asyncactivitycompletion` subdirectory.
   1. Observe that the Workflow's `StartToCloseTimeout` has been lengthened to 300 seconds for this exercise. Activities can still time out if they are running in the background.
   1. Close this file without making any changes.

## Part C: Configure a Client to send CompleteActivity

1. Open the `CompletionClient.java` file in the `src/main/java/asyncactivitycompletion` subdirectory.
1. The first thing you'll need to do is add some way of supplying the `taskToken` specific to the Activity you are trying to complete at runtime. In a production system, you might store and retrieve the token from a database, but for now, you can configure this Client to accept it as a command line arguement. Read in the token from the command line `args[0]` and decode the base 64, storing it in a `byte[]`. Hint, invert the call in `AsyncActivityCompletionActivitiesImpl.java`
1. Add a call to the `doNotCompleteOnReturn();` method using the `context` object from Part A. This notifies Temporal that the Activity should not be completed on return and will be completed asynchronously.
1. Save the file.

## Part D: Running the Workflow and Completing it Asynchronously

At this point, you can run your Workflow. As with the Signal Exercise, the Workflow will not return on its own -- in this case, because your Activity is set to complete asynchronously, and will wait to receive `CompleteActivity()`.

1. In one terminal, navigate to the `practice` subdirectory
   1. If you're in the GitPod environment you can instead run `ex3`
   1. Compile the code using `mvn clean compile`
   1. Run the worker using `mvn exec:java -Dexec.mainClas='asyncactivitycompletion.AsyncActivityCompletionWorker'`.
      1. If you're in the GitPod environment you can instead run `ex3w`
1. In another terminal, navigate to the `practice` subdirectory
   1. If you're in the GitPod environment you can instead run `ex3`
   1. Invoke the Workflow using `mvn exec:java -Dexec.mainClass='asyncactivitycompletion.Starter'`
      If you're in the GitPod environment you can instead run `ex3st`
1. Navigate back to the Worker terminal. Your work will produce some logging, eventually including your `taskToken`:

```
12:06:58.679 INFO  - Async Activity Workflow started with input: Plain text input
12:06:58.684 INFO  - Activity received input: Plain text input
CiQ3NGE5NGEyMC05NjE2LTQ3YWEtOTMwMS01YmQ4MzFlZTE4M2ISF2FzeW5jLWNvbXBsZXRlLXdvcmtmbG93GiQzZWY3YjE0YS1lMzdiLTQ3NGItOTIyMS1iN2UyYjM3MGU0MGMgBSgBMiRhMzMxN2E1My0xMzMwLTM4N2YtYTBiNC1kN2RjMjAzNjAwY2NCCEFjdGl2aXR5SggIARC+gEAYAQ==
```

1. You can now use this token to send a `complete()` call from another client. In another terminal, navigate to the `practice` subdirectory
   1. If you're in the GitPod environment you can instead run `ex3`
   1. Run the command `mvn exec:java -Dexec.mainClass='asyncactivitycompletion.CompletionClient' -Dexec.args="TASK_TOKEN"` with your Task Token replacing `TASK_TOKEN` with your Task Token to complete the Activity.
      1. If you're in the GitPod environment you can instead run `ex3c TASK_TOKEN` replacing `TASK_TOKEN` with your Task Token to complete the Activity.
   1. This will cause your Activity to return and your Workflow to successfully complete. The terminal running your Worker process should now show
   ```
   12:07:43.689 INFO  - Async Activity Workflow completed with result: Asynchronously completed
   ```

### This is the end of the exercise.
