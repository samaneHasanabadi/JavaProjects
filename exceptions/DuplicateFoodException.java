package exceptions;

public class DuplicateFoodException extends Exception {
    @Override
    public String getMessage() {
        return "This Food is already exits";
    }
}
