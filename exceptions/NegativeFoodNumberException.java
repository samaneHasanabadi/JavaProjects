package exceptions;

public class NegativeFoodNumberException extends Exception{
    @Override
    public String getMessage() {
        return "Food number can not be negative";
    }
}
