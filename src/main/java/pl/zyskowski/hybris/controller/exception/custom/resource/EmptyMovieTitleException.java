package pl.zyskowski.hybris.controller.exception.custom.resource;

public class EmptyMovieTitleException extends RuntimeException {
    public EmptyMovieTitleException() {
        super("Movie title cannot be empty");
    }
}
