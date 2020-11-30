package exceptions;

public class DuplicateRestaurantException extends Exception {
    @Override
    public String getMessage() {
        return "This restaurant name is already exists";
    }
}
