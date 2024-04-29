package queryingworkflows.orderpizza;

import io.temporal.activity.ActivityInterface;
import queryingworkflows.model.Distance;
import queryingworkflows.exceptions.InvalidChargeAmountException;
import queryingworkflows.model.Address;
import queryingworkflows.model.OrderConfirmation;
import queryingworkflows.model.Bill;

@ActivityInterface
public interface PizzaActivities {

  Distance getDistance(Address address);

  OrderConfirmation sendBill(Bill bill) throws InvalidChargeAmountException;
}
