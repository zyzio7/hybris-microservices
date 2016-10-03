package pl.zyskowski.hybris.movielibrary.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Entity("movies")
public class Movie  {

    @Id
    private ObjectId id;

    @Property
    @Indexed(unique = true)
    @Size(min=3, max=50, message = "Movie title lenght must be between {min} and {max}")
    private String title;

    @Property
    private Map<String, Double> ratings = new HashMap();

    @Property
    private Double averageRating;

    @Property
    private String director;

    @Property
    private Collection<String> actors;

    @Property
    private String createdAt;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Collection<String> getActors() {
        return actors;
    }

    public void setActors(Collection<String> actors) {
        this.actors = actors;
    }

    public Date getCreatedAt() {
        return new Date(createdAt);
    }

    public void addRating(String userId, Double value) {
        ratings.put(userId, value);
        averageRating = calculateAverageRating();
    }

    private Double calculateAverageRating() {
        OptionalDouble collect = ratings.values().stream().mapToDouble(Number::doubleValue).average();
        if(collect.isPresent())
            return collect.getAsDouble();
        else
            return 0.0;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = formatter.format(createdAt);
    }

    public Double getAverageRating() {
        return averageRating;
    }

    private void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
