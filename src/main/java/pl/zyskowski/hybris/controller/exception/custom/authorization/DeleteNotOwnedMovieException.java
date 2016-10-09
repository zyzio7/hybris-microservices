package pl.zyskowski.hybris.controller.exception.custom.authorization;

import pl.zyskowski.hybris.model.Movie;

public class DeleteNotOwnedMovieException extends RuntimeException {

    public DeleteNotOwnedMovieException(final Movie movie) {
        super(String.format("Cannot delete movie: [%s], user is not movie entry owner!", movie.getTitle()));
    }

}
