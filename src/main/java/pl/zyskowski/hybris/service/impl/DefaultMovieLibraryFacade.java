package pl.zyskowski.hybris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;
import pl.zyskowski.hybris.database.MoviesDAO;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.OrderBy;
import pl.zyskowski.hybris.service.MovieLibraryFacade;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultMovieLibraryFacade implements MovieLibraryFacade {

    final MoviesDAO dao;
    final public static String MOVIE_NOT_FOUND = "Movie with title: [%s], is not persisted in database";

    @Autowired
    public DefaultMovieLibraryFacade(MoviesDAO dao) {
        this.dao = dao;
    }

    @Override
    public Movie add(final User user, final Movie movie) throws Exception {
        movie.setAddedBy(user.getId());
        return dao.addMovie(movie);
    }

    @Override
    public void remove(final User user, final String title) throws Exception{
        dao.remove(user, title);
    }

    @Override
    public Movie update(final String title, final Movie movie) throws Exception {
        final Movie updatedMovie = dao.getMovie(title);

        if(updatedMovie == null)
            throw new Exception(String.format(MOVIE_NOT_FOUND, title));

        Optional.ofNullable(movie.getTitle()).ifPresent(e -> updatedMovie.setTitle(e));
        Optional.ofNullable(movie.getActors()).ifPresent(e -> updatedMovie.setActors(e));
        Optional.ofNullable(movie.getDirector()).ifPresent(e -> updatedMovie.setDirector(e));
        Optional.ofNullable(movie.getCreatedAt()).ifPresent(e -> updatedMovie.setCreatedAt(e));
        dao.updateMovie(updatedMovie);

        return updatedMovie;
    }

    @Override
    public Movie rate(final String title, final User user, final Double rating) {
        return dao.rateMovie(title, user, rating);
    }

    @Override
    public Movie get(final String title) throws Exception{
        return Optional.ofNullable(dao.getMovie(title)).orElseThrow(() -> new Exception(String.format(MOVIE_NOT_FOUND, title)));
    }

    @Override
    public List<Movie> getAll() throws Exception {
        return dao.getAllMovies();
    }

    @Override
    public List<Movie> getAll(OrderBy orderBy) throws Exception {
        if(orderBy == null)
            return getAll();

        return dao.getSortedMovies(orderBy);
    }

}
