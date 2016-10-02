package pl.zyskowski.hybris.movielibrary.facades;

import pl.zyskowski.hybris.movielibrary.model.Movie;
import pl.zyskowski.hybris.movielibrary.utils.OrderBy;

import java.util.List;

public interface MovieLibraryFacade {

    Movie add(final Movie movie) throws Exception;
    void remove(final String title) throws Exception;
    Movie update(final String title, final Movie movie) throws Exception;
    Movie rate(final String title, Double rating) throws Exception;
    Movie get(final String title) throws Exception;
    List<Movie> getByCategory(final String title) throws Exception;
    List<Movie> getAll() throws Exception;
    List<Movie> getAll(final OrderBy orderBy) throws Exception;

}
