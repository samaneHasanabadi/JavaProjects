package exceptions;

public class NoSuchFoodException extends Exception {
    @Override
    public String getMessage() {
        return "No such food exists";
    }
}
