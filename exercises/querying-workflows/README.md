# Exercise #2: Querying Workflows

During this exercise, you will:

- Define and handle a Query
- Call the Query from the Client
- Send a Query from the Command line

Make your changes to the code in the `practice` subdirectory (look for
`TODO` comments that will guide you to where you should make changes to
the code). If you need a hint or want to verify your changes, look at
the complete version in the `solution` subdirectory.

### GitPod Environment Shortcuts

If you are executing the exercises in the provided GitPod environment, you
can take advantage of certain aliases to aid in navigation and execution of
the code.

| Command | Action                                                                                                                          |
| :------ | :------------------------------------------------------------------------------------------------------------------------------ |
| `ex2`   | Change to Exercise 2 Practice Directory                                                                                         |
| `ex2s`  | Change to Exercise 2 Solution Directory                                                                                         |
| `ex2w`  | Execute the Exercise 2 Worker. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)  |
| `ex2st` | Execute the Exercise 2 Starter. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`) |
| `ex2sg` | Send the Exercise 2 Signal. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)     |
| `ex2q`  | Send the Exercise 2 Query. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)      |

## Part A: Defining a Query

In this part of the exercise, you will define your Query.

1. Open the `QueryingWorkflowsWorkflow.java` file in the `practice/src/main/java/queryingworkflows` subdirectory
   1. Define the Query method `queryState` in the interface. Annotate the method with `@QueryMethod`. It should return a String representing the current state of the Workflow and take no parameters.
   1. Save the file.
1. Open the `SendingSignalsWorkflowImpl.java` file in the `practice/src/main/java/queryingworkflows` subdirectory.
   1. Implement the `queryState` method
      1. This method should read the instance variable `currentState` and return it
   1. Set the value of `currentState` at specific points in the Workflow:
      1. Set the value of `currentState` to `started` as the first line in the Workflow
      1. Set the value of `currentState` to `waiting for signal` right above the call to await the Signal
      1. Set the value of `currentState` to `Workflow completed` at the end of the Workflow
   1. Save the file.

## Part B: Performing a Query from a Client

In this part of the exercise, you will create another Temporal client that sends
a Query.

1. Open the `SendingSignalsWorkflowImpl.java` file in the `practice/src/main/java/queryingworkflows` subdirectory.
   1. Call the `queryState()` method using the `workflow` object.
   1. Print the result of the call to standard out.
   1. Save the file.

## Part C: Running the Workflow and the Query

At this point, you can run your Workflow. Because it is the same Workflow from the last exercise with added Query support, it will wait to receive a Signal after starting.

1. Navigate to the Exercise 2 practice directory in the terminal
   1. If you're in the GitPod environment you can instead run `ex2`
   1. Otherwise, use:
   ```bash
   cd exercises/querying-workflows/practice/src
   ```
   1. _Be sure to do this for **every** terminal_
1. Compile the code using `mvn clean compile`
1. In a terminal, run the command `mvn exec:java -Dexec.mainClass='queryingworkflows.QueryingWorkflowsWorker'` to start the Worker.
   1. Make sure you are in the correct directory by running `ex2`
   1. If you're in the GitPod environment, you can instead run `ex2w`
1. In another terminal, run the command `mvn exec:java -Dexec.mainClass='queryingworkflows.Starter'` to start the Workflow.

   1. If you're in the GitPod environment, you can run `ex2st`
   1. You should receive some logging from your Worker along these lines:

   ```bash
   16:11:45.199 INFO  - Created WorkflowServiceStubs for channel: ManagedChannelOrphanWrapper{delegate=ManagedChannelImpl{logId=1, target=127.0.0.1:7233}}
   16:11:45.428 INFO  - start: Poller{name=Workflow Poller taskQueue="signals", namespace="default", identity=12931@Masons-Laptop}
   16:11:45.431 INFO  - start: Poller{name=Activity Poller taskQueue="signals", namespace="default", identity=12931@Masons-Laptop}
   16:12:06.733 INFO  - Signal workflow started with input: Plain text input
   ```

1. You can now Query your Workflow. In a third terminal, run the command `mvn exec:java -Dexec.mainClass='queryingworkflows.QueryClient'` to send the Query
   1. Make sure you are in the correct directory by running `ex2`
   1. If you're in the GitPod environment, you can instead run `ex2q`
   1. You'll receive a result
   ```bash
   Received Query result. Result: waiting for signal
   ```

## Part D: Sending a Query from the Command Line

To send a Query from the CLI, use `temporal workflow query` with the same parameters as your client:

```bash
temporal workflow query \
    --workflow-id="query-workflow" \
    --type="queryState"
```

It will produce the same result:

```
Query result:
["waiting for signal"]
```

## Part E: Sending a Signal and Querying a Closed Workflow

Now you can send a Signal to your Workflow as in the previous exercise so it
completes successfully, then Query the closed Workflow.

1. In the same terminal you sent the Query, send the Signal to the Workflow using
   command `mvn exec:java -Dexec.mainClass='queryingworkflows.SignalClient'`.
   1. Make sure you are in the correct directory by running `ex2`
   1. If you're in the GitPod environment, you can instead run `ex2sg`
   1. You won't see any output from the Signal, but you should see the result
      of the Workflow in the terminal where the Workflow was started.
      1. The Workflow has now completed and is now in a closed state. Closed
         Workflows can also be queried.
   1. Query the Workflows using the CLI command:
   ```bash
   temporal workflow query \
    --workflow-id="query-workflow" \
    --type="queryState"
   ```
   1. You should get the result
   ```bash
   Query result:
   ["Workflow completed"]
   ```

### This is the end of the exercise.
