package customsearchattributes;

import io.temporal.activity.ActivityInterface;
import customsearchattributes.model.Distance;
import customsearchattributes.exceptions.InvalidChargeAmountException;
import customsearchattributes.model.Address;
import customsearchattributes.model.OrderConfirmation;
import customsearchattributes.model.Bill;

@ActivityInterface
public interface PizzaActivities {

  Distance getDistance(Address address);

  OrderConfirmation sendBill(Bill bill) throws InvalidChargeAmountException;
}
