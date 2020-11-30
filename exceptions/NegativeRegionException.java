package exceptions;

public class NegativeRegionException extends Exception{
    @Override
    public String getMessage() {
        return "Region can not be negative";
    }
}
