package pl.zyskowski.hybris.controller.exception.custom.resource;

import pl.zyskowski.hybris.model.Movie;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Movie movie) {
        this(movie.getTitle());
    }

    public MovieNotFoundException(String title) {
        super("Movie of title: [" + title + "] could not be found");
    }

}
