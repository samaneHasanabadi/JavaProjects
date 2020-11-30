package exceptions;

public class NoSuchFoodInBasketException extends Exception {
    @Override
    public String getMessage() {
        return "The Basket does not contains food";
    }
}
