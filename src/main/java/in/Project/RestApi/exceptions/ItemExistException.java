package in.Project.RestApi.exceptions;

public class ItemExistException extends RuntimeException{
    public ItemExistException(String message) {
        super(message);
    }
}
