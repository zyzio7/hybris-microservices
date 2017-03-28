package pl.zyskowski.hybris.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.springframework.social.facebook.api.User;

@Entity("users")
public class UserModel {

    @Id
    @JsonIgnore
    private ObjectId id;

    @Property
    private String userId;

    @Property
    private String username;

    public UserModel(String userId, String username){
        this.userId = userId;
        this.username = username;
    }

    public UserModel(User user) {
        this.userId = user.getId();
        this.username = user.getName();
    }

    public UserModel() {}

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserModel) {
            UserModel user = (UserModel)obj;
            if(userId.equals(user.getUserId()))
                return true;
        }
        return false;
    }
}
