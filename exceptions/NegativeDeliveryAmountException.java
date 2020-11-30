package exceptions;

public class NegativeDeliveryAmountException extends Exception {
    @Override
    public String getMessage() {
        return "Delivery amount can not be negative";
    }
}
