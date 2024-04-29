package sendingsignalsexternal;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

import sendingsignalsexternal.orderpizza.PizzaActivitiesImpl;
import sendingsignalsexternal.orderpizza.PizzaWorkflowImpl;
import sendingsignalsexternal.fulfillorder.FulfillOrderWorkflowImpl;
import sendingsignalsexternal.fulfillorder.FulfillOrderActivitiesImpl;

public class SendSignalExternalWorker {
  public static void main(String[] args) {

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    WorkflowClient client = WorkflowClient.newInstance(service);
    WorkerFactory factory = WorkerFactory.newInstance(client);

    Worker worker = factory.newWorker(Constants.TASK_QUEUE_NAME);

    worker.registerWorkflowImplementationTypes(PizzaWorkflowImpl.class);
    worker.registerWorkflowImplementationTypes(FulfillOrderWorkflowImpl.class);

    worker.registerActivitiesImplementations(new PizzaActivitiesImpl());
    worker.registerActivitiesImplementations(new FulfillOrderActivitiesImpl());

    factory.start();
  }
}
