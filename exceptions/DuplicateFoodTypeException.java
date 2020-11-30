package exceptions;

public class DuplicateFoodTypeException extends Exception {
    @Override
    public String getMessage() {
        return "This Food Type is already exists";
    }
}
