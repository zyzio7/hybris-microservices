package pl.zyskowski.hybris.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.springframework.social.facebook.api.User;

import javax.validation.constraints.Size;
import java.text.DecimalFormat;
import java.util.*;

@Entity("movie")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie  {

    @Id
    @JsonIgnore
    private ObjectId id;

    @Property
    @Size(min=3, max=50, message = "Movie title lenght must be between {min} and {max}")
    @Indexed(unique = true)
    private String title;

    @Reference
    private Collection<RatingModel> ratings;

    @Property
    private Double averageRating;

    @Property
    private String director;

    @Embedded
    private Collection<String> actors;

    @Property
    private Date createdAt;

    @Property
    private Category category;

    @Reference
    private UserModel addedBy;

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

    public Collection<RatingModel> getRatings() {
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UserModel getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(UserModel userModel) {
        this.addedBy = userModel;
    }

    public RatingModel addRating(final UserModel user, final Integer value) {
        if (ratings == null)
            ratings = new ArrayList<>();

        Optional<RatingModel> rating = getUserRating(user);
        if(rating.isPresent()) {
            rating.get().setRate(value);
        } else {
            rating = Optional.of(new RatingModel(user, value));
            ratings.add(rating.get());
        }

        averageRating = calculateAverageRating();
        return rating.get();
    }

    private Optional<RatingModel> getUserRating(final UserModel userModel) {
        return ratings.stream().filter(rate -> rate.getUserModel().equals(userModel)).findFirst();
    }

    private Double calculateAverageRating() {
        OptionalDouble collect = ratings.stream().mapToDouble(RatingModel::getRate).average();
        final DecimalFormat df = new DecimalFormat();
        if(collect.isPresent())
            return collect.getAsDouble();
        else
            return 0.0;
    }

}
