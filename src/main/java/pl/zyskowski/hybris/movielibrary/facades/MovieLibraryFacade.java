package pl.zyskowski.hybris.movielibrary.facades;

import pl.zyskowski.hybris.movielibrary.model.Movie;

public interface MovieLibraryFacade {

    void add(final Movie movie) throws Exception;
    void remove(final String title);
    void update(final Movie movie);
    void rate(final String title, Double rating);
}
