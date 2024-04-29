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
| `ex3`   | Change to Exercise 3 Practice Directory                                                                                         |
| `ex3s`  | Change to Exercise 3 Solution Directory                                                                                         |
| `ex3w`  | Execute the Exercise 3 Worker. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)  |
| `ex3st` | Execute the Exercise 3 Starter. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`) |
| `ex3sg` | Send the Exercise 3 Signal. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)     |
| `ex3q`  | Send the Exercise 3 Query. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)      |

## Part A: Defining a Query

In this part of the exercise, you will define your Query.

1. Open the `PizzaWorkflow.java` file in the `practice/src/main/java/queryingworkflows` subdirectory
   1. Define the Query method `orderStatus` in the interface. Annotate the method with `@QueryMethod`. It should return a String representing the current status of the order and take no parameters.
   2. Save the file.
1. Open the `PizzaWorkflowImpl.java` file in the `practice/src/main/java/queryingworkflows` subdirectory.
   1. Implement the `orderStatus` method
      1. This method should read the instance variable `status` and return it
   2. Set the value of `status` at specific points in the Workflow:
      1. Set the value of `status` to `Started` as the first line in the Workflow
      1. Set the value of `status` to `Out for delivery` right above the call to await the Signal
      1. Set the value of `status` to `Order complete` as the last line in the `try` block where the `sendBill()` Activity was invoked
      1. **Note**: You can set the value of status anywhere for whichever states you would like to report on. Feel free with experimenting and adding more statuses
   3. Save the file.

## Part B: Performing a Query from a Client

In this part of the exercise, you will create another Temporal client that sends
a Query.

1. Open the `QueryClient.java` file in the `practice/src/main/java/queryingworkflows` subdirectory.
   1. Call the `orderStatus()` method using the `workflow` object.
   2. Print the result of the call to standard out.
   3. Save the file.

## Part C: Running the Workflow and the Query

At this point, you can run your Workflow. Because it is the same Workflow from the last exercise with added Query support, it will wait to receive a Signal after starting.

1. Navigate to the Exercise 3 practice directory in the terminal
   1. If you're in the GitPod environment you can instead run `ex3`
   2. Otherwise, use:
   ```bash
   cd exercises/querying-workflows/practice/src
   ```
   3. _Be sure to do this for **every** terminal_
2. Compile the code using `mvn clean compile`
3. In a terminal, run the command `mvn exec:java -Dexec.mainClass='queryingworkflows.QueryingWorkflowsWorker'` to start the Worker.
   1. Make sure you are in the correct directory by running `ex3`
   2. If you're in the GitPod environment, you can instead run `ex3w`
4. In another terminal, run the command `mvn exec:java -Dexec.mainClass='queryingworkflows.Starter'` to start the Workflow.

   1. If you're in the GitPod environment, you can run `ex2st`
   2. You should receive some logging from your Worker along these lines:

   ```bash
   10:15:51.081 INFO  - Created WorkflowServiceStubs for channel: ManagedChannelOrphanWrapper{delegate=ManagedChannelImpl{logId=1, target=127.0.0.1:7233}}
   10:15:51.345 INFO  - start: Poller{name=Workflow Poller taskQueue="pizza-tasks", namespace="default", identity=63174@Masons-Laptop}
   10:15:51.349 INFO  - start: Poller{name=Activity Poller taskQueue="pizza-tasks", namespace="default", identity=63174@Masons-Laptop}
   ```

5. You can now Query your Workflow. In a third terminal, run the command `mvn exec:java -Dexec.mainClass='queryingworkflows.QueryClient'` to send the Query
   1. Make sure you are in the correct directory by running `ex3`
   2. If you're in the GitPod environment, you can instead run `ex3q`
   3. You'll receive a result
   ```bash
   Out for delivery
   ```

## Part D: Sending a Query from the Command Line

To send a Query from the CLI, use `temporal workflow query` with the same parameters as your client:

```bash
temporal workflow query \
    --workflow-id="pizza-workflow-order-XD001" \
    --type="queryState"
```

It will produce the same result:

```
Query result:
["Out for delivery"]
```

## Part E: Sending a Signal and Querying a Closed Workflow

Now you can send a Signal to your Workflow as in the previous exercise so it
completes successfully, then Query the closed Workflow.

1. In the same terminal you sent the Query, send the Signal to the Workflow using
   command `mvn exec:java -Dexec.mainClass='queryingworkflows.SignalClient'`.
   1. Make sure you are in the correct directory by running `ex3`
   2. If you're in the GitPod environment, you can instead run `ex3sg`
   3. You won't see any output from the Signal, but you should see the result
      of the Workflow in the terminal where the Workflow was started.
      1. The Workflow has now completed and is now in a closed state. Closed
         Workflows can also be queried.
         1. If you send the Signal and Query quickly enough, you may Query the Workflows
            before the Workflow has closed. While that's unlikely in this case, keep
            this in mind for your future implementations.
   4. Query the Workflows using the CLI command:
   ```bash
   temporal workflow query \
    --workflow-id="pizza-workflow-order-XD001" \
    --type="orderStatus"
   ```
   5. You should get the result
   ```bash
   Query result:
   ["Order complete"]
   ```

### This is the end of the exercise.
