package pl.zyskowski.hybris.controller.exception.custom.authorization;

public class UserDataNotStoredException extends RuntimeException {

    public UserDataNotStoredException() {
        super("User data has is not stored in system");
    }

    public UserDataNotStoredException(String message) {
        super(message);
    }

}
