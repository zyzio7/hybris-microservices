package pl.zyskowski.hybris.database;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;
import pl.zyskowski.hybris.model.Category;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.OrderBy;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        if(datastore != null)
            return;

        final Morphia morphia = new Morphia();
        morphia.mapPackage("pl.zyskowski.hybris.model");
        System.out.println("DB URL TO: " + dbUrl);
        ServerAddress serverAddress = new ServerAddress(dbUrl, dbPort);
        List<MongoCredential> credentials = new ArrayList();
        MongoCredential credential = MongoCredential.createCredential(dbUsername, dbName, dbPassword.toCharArray());
        credentials.add(credential);
        datastore = morphia.createDatastore(new MongoClient(serverAddress, credentials), dbName);
        datastore.ensureIndexes();
    }

    public Movie getMovie(final String title) {
        Query<Movie> query = datastore.createQuery(Movie.class).field("title").equalIgnoreCase(title);
        return query.get();
    }

    public Movie addMovie(@Valid final Movie movie) {
        datastore.save(movie);
        return movie;
    }

    synchronized public void remove(final User user, final String title) throws Exception {
        final Movie movie = getMovie(title);
        if (movie == null)
            throw new Exception(String.format("Movie with title: [%s], is not persisted in database", title));
        if (!user.getId().equals(movie.getAddedBy()))
            throw new Exception("Authenticated user is not an creator entry");
        datastore.delete(movie);
    }

    public Movie updateMovie(@Valid final Movie updatedMovie) {
        return addMovie(updatedMovie);
    }

    public List<Movie> getAllMovies() {
        return datastore.createQuery(Movie.class).asList();
    }

    public List<Movie> getSortedMovies(final OrderBy orderBy) {
        return datastore.createQuery(Movie.class).order(orderBy.toString()).asList();
    }

    public void prepareTestData() {
        Movie movie = new Movie();
        movie.setTitle("The Green Mile");
        movie.setCreatedAt(new Date());
        movie.setCategory(Category.THRILLER);
        String[] actors = new String[]{"Paul Edgecombe", "John Coffey"};
        movie.setActors( Arrays.asList(actors));
//        datastore.save(movie);
    }

    synchronized public Movie rateMovie(final String title, final User user, Double rating) {
        final Movie dbMovie = getMovie(title);
        dbMovie.addRating(user.getId(), rating);
        datastore.save(dbMovie);
        return dbMovie;
    }
}
