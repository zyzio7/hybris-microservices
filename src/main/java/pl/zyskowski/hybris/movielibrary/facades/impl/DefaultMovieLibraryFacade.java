package pl.zyskowski.hybris.movielibrary.facades.impl;

import org.springframework.stereotype.Component;
import pl.zyskowski.hybris.database.MoviesDAO;
import pl.zyskowski.hybris.movielibrary.facades.MovieLibraryFacade;
import pl.zyskowski.hybris.movielibrary.model.Movie;
import pl.zyskowski.hybris.movielibrary.utils.OrderBy;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultMovieLibraryFacade implements MovieLibraryFacade {

    final MoviesDAO dao = MoviesDAO.getInstance();
    final public static String MOVIE_NOT_FOUND = "Movie with title: [%s], is not persisted in database";

    @Override
    public Movie add(final Movie movie) throws Exception {
        Integer titleLength = Optional.ofNullable(movie.getTitle()).map(e -> e.length()).orElse(0);
        if (titleLength < 3 || titleLength > 50)
            throw new Exception("Title length should be between 3 and 50");
        return dao.addMovie(movie);
    }

    @Override
    public void remove(final String title) throws Exception{
        MoviesDAO.getInstance().remove(title);
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
        Optional.ofNullable(movie.getRating()).ifPresent(e -> updatedMovie.setRating(e));
        dao.updateMovie(updatedMovie);

        return updatedMovie;
    }

    @Override
    public Movie rate(String title, Double rating) {

        return null;
    }

    @Override
    public Movie get(String title) throws Exception{
        return Optional.ofNullable(dao.getMovie(title)).orElseThrow(() -> new Exception(String.format(MOVIE_NOT_FOUND, title)));
    }

    @Override
    public List<Movie> getByCategory(String title) throws Exception {
        return null;
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
