## Exercise #2: Sending a Signal from the Client

This exercise shows how to send a Signal from just the Client and not from another Workflow.

During this exercise, you will:

- Retrieve a stub on a running Workflow to Signal
- Send a Signal from the Client

In this part of the exercise, we have removed the `FulfillOrderWorkflow` Workflow from the previous exercise, since this exercise is just for sending a Signal from a Client, and not from another Workflow.

We will skip the defining and handling of `FulfillOrderSignal` that were handled in `src/main/java/sendingsignalsclient/orderpizza`. This exercise will solely focus on the Client file.

### GitPod Environment Shortcuts

If you are executing the exercises in the provided GitPod environment, you
can take advantage of certain aliases to aid in navigation and execution of
the code.

| Command | Action                                                                                                                             |
| :------ | :--------------------------------------------------------------------------------------------------------------------------------- |
| `ex2`   | Change to Exercise 1 Practice Directory                                                                                            |
| `ex2s`  | Change to Exercise 1 Solution Directory                                                                                            |
| `ex2w`  | Execute the Exercise 1 Worker. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)     |
| `ex2st` | Execute the Exercise 1 Starter. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)    |
| `ex2sg` | Send the Exercise 1 Signal. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`) |


## Part A: Create a Stub on the Pizza Workflow

In this part of the exercise, you will create a stub on the Workflow that you wish to Signal, which is `PizzaWorkflow`.

1. Recall that the Workflow ID for this Workflow is `pizza-workflow-order-XD001`
1. Edit the `SignalClient.java` in the `src/main/java/sendingsignalsclient` subdirectory and modify the `newWorkflowStub()` method to get a stub for that Workflow
   1. Change the Workflow ID from `CHANGE_ME` to the Workflow ID above
1. Save the file

## Part B: Sending a Signal to the Pizza Workflow

Now that you have a stub for the Workflow you wish to Signal (`PizzaWorkflow`), we will now send `fulfillOrderSignal`.

1. Continue editing the `SignalClient.java` in the `src/main/java/sendingsignalsclient` subdirectory
1. Using the stub defined above, send the Signal by calling the `fulfillOrderSignal()` method, passing `true` as a parameter
1. Save the file


## Part C: Run the Workflow

To run the Workflow:

1. In all terminals, change directories to the Exercise 2 directory `sending-signals-external`
   1. If you're in the GitPod environment, you can run `ex2`
1. In one terminal, compile the code using `mvn clean compile`
1. In the same terminal, start the Worker by running `mvn exec:java -Dexec.mainClass="sendingsignalsclient.SendSignalClientWorker"`
   1. If you're in the GitPod environment, you can instead run `ex2w` to start the Worker
1. In another terminal, start the Workflow by running `mvn exec:java -Dexec.mainClass="sendingsignalsclient.Starter"`. This will start the Workflow, which you will see waiting for a Signal
   1. If you're in the GitPod environment, you can instead run `ex2st` to start the Workflow
1. In another terminal, send the signal by running `mvn exec:java -Dexec.mainClass="sendingsignalsclient.SignalClient"`
   1. If you're in the GitPod environment, you can instead run `ex2sg` to send the Signal
1. You should see the following output in the terminal where you ran the Starter:

```bash
09:41:34.326 INFO  - Created WorkflowServiceStubs for channel: ManagedChannelOrphanWrapper{delegate=ManagedChannelImpl{logId=1, target=127.0.0.1:7233}}
Workflow result: OrderConfirmation{orderNumber='XD001', status='SUCCESS', confirmationNumber='P24601', billingTimestamp=1712500897, amount=3500}
```

### This is the end of the exercise.
