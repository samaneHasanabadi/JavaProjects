package exceptions;

public class NegetiveFoodPriceException extends Exception{
    @Override
    public String getMessage() {
        String message = "Food price can not be negative";
        return message;
    }
}
