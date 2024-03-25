package queryingworkflows;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class QueryingWorkflowsWorker {
  public static void main(String[] args) {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    WorkflowClient client = WorkflowClient.newInstance(service);
    WorkerFactory factory = WorkerFactory.newInstance(client);

    Worker worker = factory.newWorker("signals");

    worker.registerWorkflowImplementationTypes(QueryingWorkflowsWorkflowImpl.class);

    worker.registerActivitiesImplementations(new QueryingWorkflowsActivitiesImpl());

    factory.start();
  }
}
