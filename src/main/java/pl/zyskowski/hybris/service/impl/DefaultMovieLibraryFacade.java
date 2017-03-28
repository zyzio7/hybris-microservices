package pl.zyskowski.hybris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieNotFoundException;
import pl.zyskowski.hybris.database.MoviesDAO;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.OrderBy;
import pl.zyskowski.hybris.model.UserModel;
import pl.zyskowski.hybris.service.MovieLibraryFacade;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultMovieLibraryFacade implements MovieLibraryFacade {

    @Autowired
    private MoviesDAO dao;

    @Override
    public Movie add(final UserModel user, final Movie movie) {
        movie.setAddedBy(user);
        return getDao().addMovie(movie);
    }

    @Override
    public void remove(final UserModel user, final String title) {
        getDao().remove(user, title);
    }

    @Override
    public Movie update(final String title, final Movie movie) throws Exception {
        final Movie updatedMovie = getDao().getMovie(title);

        if(updatedMovie == null)
            throw new MovieNotFoundException(title);

        Optional.ofNullable(movie.getTitle()).ifPresent(e -> updatedMovie.setTitle(e));
        Optional.ofNullable(movie.getActors()).ifPresent(e -> updatedMovie.setActors(e));
        Optional.ofNullable(movie.getDirector()).ifPresent(e -> updatedMovie.setDirector(e));
        Optional.ofNullable(movie.getCategory()).ifPresent(e -> updatedMovie.setCategory(e));
        Optional.ofNullable(movie.getCreatedAt()).ifPresent(e -> updatedMovie.setCreatedAt(e));
        getDao().updateMovie(updatedMovie);

        return updatedMovie;
    }

    @Override
    public Movie rate(final String title, final UserModel user, final Integer rating) {
        return getDao().rateMovie(title, user, rating);
    }

    @Override
    public Movie get(final String title) {
        return getDao().getMovie(title);
    }

    @Override
    public List<Movie> getAll() {
        return getDao().getAllMovies();
    }

    @Override
    public List<Movie> getAll(OrderBy orderBy) {
        if(orderBy == null)
            return getAll();
        return getDao().getSortedMovies(orderBy);
    }

    public MoviesDAO getDao() {  return dao; }
}
