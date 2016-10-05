package pl.zyskowski.hybris.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import javax.validation.constraints.Size;
import java.text.DecimalFormat;
import java.util.*;

@Entity("movie")
public class Movie  {

    @Id
    private ObjectId id;

    @Property
    @Size(min=3, max=50, message = "Movie title lenght must be between {min} and {max}")
    @Indexed(unique = true)
    private String title;

    @Property
    private String addedBy;

    @Property
    private Map<String, Double> ratings = new HashMap();

    @Property
    private Double averageRating;

    @Property
    private String director;

    @Property
    private Collection<String> actors;

    @Property
    private Date createdAt;

    @Property
    private Category category;

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
        return createdAt;
    }

    static Integer counter = 1;
    public void addRating(String userId, Double value) {
        ratings.put(userId, value);
        averageRating = calculateAverageRating();
    }

    private Double calculateAverageRating() {
        OptionalDouble collect = ratings.values().stream().mapToDouble(Number::doubleValue).average();
        DecimalFormat df = new DecimalFormat();
        if(collect.isPresent())
            return collect.getAsDouble();
        else
            return 0.0;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    private void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
