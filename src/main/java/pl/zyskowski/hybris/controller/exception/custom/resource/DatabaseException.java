package pl.zyskowski.hybris.controller.exception.custom.resource;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
