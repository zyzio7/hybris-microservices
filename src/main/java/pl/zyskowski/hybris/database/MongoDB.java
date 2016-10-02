package pl.zyskowski.hybris.database;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import pl.zyskowski.hybris.movielibrary.model.Movie;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public class MongoDB {

    private static String dbUrl = (Optional.ofNullable(System.getenv("MONGODB_URI"))).orElse("localhost");
    private static MongoDB ourInstance = new MongoDB();
    private Datastore datastore;

    public static MongoDB getInstance() {
        return ourInstance;
    }

    private MongoDB() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("pl.zyskowski.hybris.movielibrary.model");
        datastore = morphia.createDatastore(new MongoClient(), "morphia_example");
        datastore.ensureIndexes();
    }

}
