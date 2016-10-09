package pl.zyskowski.hybris.controller.exception.custom.resource;

import pl.zyskowski.hybris.model.Movie;

public class MovieAlreadyExist extends RuntimeException {

    public MovieAlreadyExist(String title) {
        super(String.format("Movie of title [%s], already exist!", title));
    }

    public MovieAlreadyExist(Movie movie) {
        this(movie.getTitle());
    }

}
