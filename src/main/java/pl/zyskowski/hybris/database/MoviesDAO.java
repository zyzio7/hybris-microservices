package pl.zyskowski.hybris.database;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import pl.zyskowski.hybris.movielibrary.model.Movie;
import pl.zyskowski.hybris.movielibrary.utils.OrderBy;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public class MoviesDAO {

    private static String dbUrl = Optional.ofNullable(System.getenv("MONGODB_URI")).orElse("localhost");
    private static MoviesDAO ourInstance = new MoviesDAO();
    private Datastore datastore;

    public static MoviesDAO getInstance() {
        return ourInstance;
    }

    private MoviesDAO() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("pl.zyskowski.hybris.movielibrary.model");
        datastore = morphia.createDatastore(new MongoClient(), "morphia_example");
        datastore.ensureIndexes();
    }

    public Movie getMovie(final String title) {
        Query<Movie> query = datastore.createQuery(Movie.class).field("title").equalIgnoreCase(title);
        return query.get();
    }

    public Movie addMovie(final Movie movie) {
        datastore.save(movie);
        return movie;
    }

    public void remove(final String title) throws Exception {
        final Movie movie = getMovie(title);
        if (movie != null)
            datastore.delete(movie);
        else
            throw new Exception(String.format("Movie with title: [%s], is not persisted in database", title));
    }

    public Movie updateMovie(final Movie updatedMovie) {
        return addMovie(updatedMovie);
    }

    public List<Movie> getAllMovies() {
        return datastore.createQuery(Movie.class).asList();
    }

    public List<Movie> getSortedMovies(final OrderBy orderBy) {
        return datastore.createQuery(Movie.class).order(orderBy.toString()).asList();
    }




    public void test() {
        Movie movie = new Movie();
        movie.setTitle("pila");
        movie.setRating(3.53);
        datastore.save(movie);

        final Query<Movie> query = datastore.createQuery(Movie.class);
        final List<Movie> employees = query.asList();
    }
}
