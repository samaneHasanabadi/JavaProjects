package exceptions;

public class NoSuchRestaurantException extends Exception {
    @Override
    public String getMessage() {
        return "No such restaurant exists";
    }
}
