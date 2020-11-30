package exceptions;

public class NoSuchFoodTypeException extends Exception{
    @Override
    public String getMessage() {
        return "This Food Type does not exist";
    }
}
