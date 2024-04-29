package sendingsignalsclient.orderpizza;

import io.temporal.activity.ActivityInterface;
import sendingsignalsclient.model.Distance;
import sendingsignalsclient.exceptions.InvalidChargeAmountException;
import sendingsignalsclient.model.Address;
import sendingsignalsclient.model.OrderConfirmation;
import sendingsignalsclient.model.Bill;

@ActivityInterface
public interface PizzaActivities {

  Distance getDistance(Address address);

  OrderConfirmation sendBill(Bill bill) throws InvalidChargeAmountException;
}
