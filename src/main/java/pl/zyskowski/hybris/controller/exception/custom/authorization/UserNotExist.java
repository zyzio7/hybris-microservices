package pl.zyskowski.hybris.controller.exception.custom.authorization;

public class UserNotExist extends RuntimeException {

    public UserNotExist() {
        super("Given token could not be matched with user account");
    }

}
