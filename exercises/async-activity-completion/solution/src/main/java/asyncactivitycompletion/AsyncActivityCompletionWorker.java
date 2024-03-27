package asyncactivitycompletion;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.client.ActivityCompletionClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class AsyncActivityCompletionWorker {
  public static void main(String[] args) {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    WorkflowClient client = WorkflowClient.newInstance(service);
    WorkerFactory factory = WorkerFactory.newInstance(client);

    Worker worker = factory.newWorker("async-complete");

    worker.registerWorkflowImplementationTypes(AsyncActivityCompletionWorkflowImpl.class);

    ActivityCompletionClient completionClient = client.newActivityCompletionClient();

    worker.registerActivitiesImplementations(new AsyncActivityCompletionActivitiesImpl(completionClient));

    factory.start();
  }
}
