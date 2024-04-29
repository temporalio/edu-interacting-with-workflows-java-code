package sendingsignalsexternal.orderpizza;

import io.temporal.activity.ActivityInterface;
import sendingsignalsexternal.model.Distance;
import sendingsignalsexternal.exceptions.InvalidChargeAmountException;
import sendingsignalsexternal.model.Address;
import sendingsignalsexternal.model.OrderConfirmation;
import sendingsignalsexternal.model.Bill;

@ActivityInterface
public interface PizzaActivities {

  Distance getDistance(Address address);

  OrderConfirmation sendBill(Bill bill) throws InvalidChargeAmountException;
}
