## Exercise #1: Sending an External Signal

During this exercise, you will:

- Define and implement a Signal handler
- Retrieve a handle on the Workflow to Signal
- Send an external Signal from another workflow
- Use a Temporal Client to submit execution requests for both Workflows

Make your changes to the code in the `practice` subdirectory (look for
`TODO` comments that will guide you to where you should make changes to
the code). If you need a hint or want to verify your changes, look at
the complete version in the `solution` subdirectory.

### GitPod Environment Shortcuts

If you are executing the exercises in the provided GitPod environment, you
can take advantage of certain aliases to aid in navigation and execution of
the code.

| Command | Action                                                                                                                             |
| :------ | :--------------------------------------------------------------------------------------------------------------------------------- |
| `ex1`   | Change to Exercise 1 Practice Directory                                                                                            |
| `ex1s`  | Change to Exercise 1 Solution Directory                                                                                            |
| `ex1w`  | Execute the Exercise 1 Worker. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)     |
| `ex1st` | Execute the Exercise 1 Starter. Must be within the appropriate directory for this to succeed. (either `practice` or `solution`)    |

## Part A: Defining a Signal handler

In this part of the exercise, you will define your Signal.

1. Open the `PizzaWorkflow.java` file in the `practice/src/main/java/sendingsignalsexternal/orderpizza` subdirectory.
1. Define the void method `fulfillOrderSignal` in the interface. Annotate the method with `@SignalMethod`. It should take a single boolean that signifies if the order was fulfilled.
1. Save the file.
1. Open the `PizzaWorkflowImpl.java` file in the `practice/src/main/java/sendingsignalsexternal/orderpizza` subdirectory.
1. Implement the `fulfillOrderSignal` method
   1. This method should set the instance variable `fulfilled` the the value
      of the boolean that was passed in as a parameter.

## Part B: Handling the Signal

You will now handle the Signal you defined in part A, and let the Workflow know what to do when it encounters the `fulfillOrderSignal`.

1. Open the `PizzaWorkflowImpl.java` file in the `practice/src/main/java/sendingsignalsexternal/orderpizza` subdirectory.
   1. In , locate the `Workflow.await(() -> this.fulfilled);` call in the `workflow` method. This will block the Workflow until a Signal is received.
   1. Locate the code that crafts the `Bill` object and the `try/catch` block that invokes the `sendBill()` method:
      1. Wrap this call in an if statement that checks to see if the value of the instance variable `fulfilled` is true. If so, execute the activity.
      1. Add a logging statement within the `try/catch` statement stating if the Workflow execution was complete or not and provide the result.
1. Save the file.

## Part C: Create a Stub on the Pizza Workflow

The `FulfillOrderWorkflow` Workflow is used to fulfill an order (making and delivering Pizzas). If these steps are completed successfully, we want to use `FulfillOrderWorkflow` to signal to `PizzaWorkflow` that the order was complete and the customer can now be billed. Note that both `FulfillOrderWorkflow` and `PizzaWorkflow` take in the same input parameter: the order details. However, `FulfillOrderWorkflow` takes another parameter, the Workflow ID.

In this part of the exercise, you will create a stub on the Workflow that you wish to Signal, which is `pizzaWorkflow`.

1. Open the `FullfillOrderWorkflowImpl.java` file in the `practice/src/main/java/sendingsignalsexternal/fulfillorder` subdirectory
1. Insert the following line of code
  ```java
  PizzaWorkflow workflow = Workflow.newExternalWorkflowStub(PizzaWorkflow.class, workflowID);
  ```
  to create a stub to retrieve a handle of `pizzaWorkflow` using its Workflow ID.
1. Save the file, but leave it open for the next part.

## Part D: Signaling Your Workflow

Now that you have a stub on the Workflow you wish to Signal (`pizzaWorkflow`), we will now send `pizzaWorkflow` a Signal.

After you have created a handle, you can see there is some logic to make and deliver the pizzas. Once that is done successfully, the Signal should be sent.

1. After the successful invocations of the activities, use the `workflow` object to call the `fulfillOrderSignal()` method, sending the value of `true`
1. Within the `catch` block, use the `workflow` object to call the `fulfillOrderSignal()` method but instead send the value of `false` indicating the Workflow was not successful
3. Save the file.

## Part E: Start the Workers

At this point, you can start the Worker that registers your Workflows and Activities. You may notice that you only have to start one Worker. A Worker can register multiple Workflows and Activities. For this exercise we have the Worker register all Workflows and Activties.

1. Navigate to the Exercise 1 practice directory in the terminal
   1. If you're in the GitPod environment you can instead run `ex1`
   1. Otherwise, use:
   ```bash
   cd exercises/sending-signals-external/practice/src
   ```
   1. _Be sure to do this for **every** terminal_
1. Compile the code using `mvn clean compile`
1. In a terminal, run the command `mvn exec:java -Dexec.mainClass="sendingsignalsexternal.SendSignalExternalWorker"` to start the Worker.
   1. Make sure you are in the correct directory by running `ex1`
   1. If you're in the GitPod environment, you can instead run `ex1w`
1. You should see output similar to the following from your Worker:
```
13:37:43.034 INFO  - Created WorkflowServiceStubs for channel: ManagedChannelOrphanWrapper{delegate=ManagedChannelImpl{logId=1, target=127.0.0.1:7233}}
13:37:43.301 INFO  - start: Poller{name=Workflow Poller taskQueue="pizza-tasks", namespace="default", identity=15863@Masons-Laptop}
13:37:43.304 INFO  - start: Poller{name=Activity Poller taskQueue="pizza-tasks", namespace="default", identity=15863@Masons-Laptop}
```


## Part F: Run Both Workflows

Now it is time to start the Workflows. In `Starter.java` you'll see that we start both Workflows, one right after the other. This is done Asynchronously so the PizzaOrderWorkflow doesn't block, waiting on the Signal from the FulfillOrderWorkflow. 

```java
CompletableFuture<OrderConfirmation> orderConfirmation = WorkflowClient.execute(pizzaWorkflow::orderPizza, order);

CompletableFuture<String> fulfillOrderResult = WorkflowClient.execute(orderWorkflow::fulfillOrder, order,
    pizzaWorkflowID);
```

These Workflows could be started in separate files and even run on separate machines. For the sake of Simplicity they are combined in this exercise.

To start both Workflows, run the following commands _once_:

1. In another terminal, run the command `mvn exec:java -Dexec.mainClass="sendingsignalsexternal.Starter"` to start both Workflows.
   1. If you're in the GitPod environment, you can run `ex1st`
   1. You should receive some logging from your Worker along these lines when the Workflow has finished:
   ```bash
      13:39:44.222 INFO  - orderPizza Workflow Invoked
      13:39:44.257 INFO  - getDistance invoked; determining distance to customer address
      13:39:44.257 INFO  - getDistance complete: 25
      13:39:44.257 INFO  - Starting to make pizzas for order XD001
      13:39:44.257 INFO  - Making pizza: Large, with mushrooms and onions
      13:39:44.257 INFO  - Making pizza: Small, with pepperoni
      13:39:44.257 INFO  - Making pizza: Medium, with extra cheese
      13:39:44.257 INFO  - All pizzas for order XD001 are ready!
      13:39:44.265 INFO  - distance is 25
      13:39:44.268 INFO  - Starting delivery XD001 to 742 Evergreen Terrace Apartment 221B Albuquerque NM 87101
      13:39:44.282 INFO  - sendBill invoked: customer: 8675309 amount: 4000
      13:39:44.282 INFO  - Applying discount
      13:39:44.286 INFO  - Bill sent to customer 8675309
   ```

### This is the end of the exercise.
