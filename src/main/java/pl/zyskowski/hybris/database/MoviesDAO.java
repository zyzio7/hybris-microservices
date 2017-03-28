package pl.zyskowski.hybris.database;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;
import pl.zyskowski.hybris.controller.exception.custom.resource.DatabaseException;
import pl.zyskowski.hybris.controller.exception.custom.authorization.DeleteNotOwnedMovieException;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieAlreadyExist;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieNotFoundException;
import pl.zyskowski.hybris.controller.exception.custom.resource.EmptyMovieTitleException;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.OrderBy;
import pl.zyskowski.hybris.model.RatingModel;
import pl.zyskowski.hybris.model.UserModel;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class MoviesDAO {

    @Value("${mongo.username}")
    private String dbUsername;
    @Value("${mongo.password}")
    private String dbPassword;
    @Value("${mongo.url}")
    private String dbUrl;
    @Value("${mongo.port}")
    private Integer dbPort;
    @Value("${mongo.name}")
    private String dbName;
    private static Datastore datastore;

    public MoviesDAO() {
    }

    @PostConstruct
    private void setData() {
        try {
            final Morphia morphia = new Morphia();
            morphia.mapPackage("pl.zyskowski.hybris.model");
            System.out.println("DB URL TO: " + dbUrl);
            ServerAddress serverAddress = new ServerAddress(dbUrl, dbPort);
            List<MongoCredential> credentials = new ArrayList();
            MongoCredential credential = MongoCredential.createCredential(dbUsername, dbName, dbPassword.toCharArray());
            credentials.add(credential);
            datastore = morphia.createDatastore(new MongoClient(serverAddress, credentials), dbName);
            datastore.ensureIndexes();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Movie getMovie(final String title) {
        Query<Movie> query = datastore.createQuery(Movie.class).field("title").equalIgnoreCase(title);
        return query.get();
    }

    public Movie addMovie(final Movie movie) {
        if(movie.getTitle() == null)
            throw new EmptyMovieTitleException();
        if (getMovie(movie.getTitle()) != null)
            throw new MovieAlreadyExist(movie.getTitle());
        datastore.save(movie.getAddedBy());
        datastore.save(movie);
        return movie;
    }

    synchronized public void remove(final User user, final String title) {
        final Movie movie = getMovie(title);
        if (movie == null)
            throw new MovieNotFoundException(title);
        if (!user.getId().equals(movie.getAddedBy()))
            throw new DeleteNotOwnedMovieException(movie);
        datastore.delete(movie);
    }

    public Movie updateMovie(final Movie updatedMovie) {
        datastore.save(updatedMovie);
        return updatedMovie;
    }

    public List<Movie> getAllMovies() {
        return datastore.createQuery(Movie.class).asList();
    }

    public List<Movie> getSortedMovies(final OrderBy orderBy) {
        return datastore.createQuery(Movie.class).order(orderBy.toString()).asList();
    }

    synchronized public Movie rateMovie(final String title, final User user, Integer rating) {
        final Movie dbMovie = getMovie(title);
        if(dbMovie == null)
            throw new MovieNotFoundException(title);
        RatingModel ratingModel = dbMovie.addRating(new UserModel(user), rating);
        datastore.save(ratingModel.getUserModel());
        datastore.save(ratingModel);
        datastore.save(dbMovie);
        return dbMovie;
    }
}
