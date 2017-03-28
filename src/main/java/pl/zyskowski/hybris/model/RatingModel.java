package pl.zyskowski.hybris.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.social.facebook.api.User;

@Entity("ratings")
public class RatingModel {

    @Id
    @JsonIgnore
    private ObjectId id;

    @Property
    private Integer rate;

    @Reference
    private UserModel userModel;

    public RatingModel(User user, int rate) {
        this.rate = rate;
        this.userModel = new UserModel(user);
    }

    public RatingModel(UserModel user, int rate) {
        this.rate = rate;
        this.userModel = user;
    }

    public RatingModel() {}


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
