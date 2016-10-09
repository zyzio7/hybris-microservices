package pl.zyskowski.hybris.access;

import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import pl.zyskowski.hybris.controller.exception.custom.authorization.UserDataNotStoredException;
import pl.zyskowski.hybris.controller.exception.custom.authorization.UserNotExist;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AuthenticationRepository {

    private static AtomicReference<AuthenticationRepository> INSTANCE = new AtomicReference<AuthenticationRepository>();
    final Map<String, String> credentials = new HashMap();
    Integer counter = 0;

    public AuthenticationRepository() {
        final AuthenticationRepository previous = INSTANCE.getAndSet(this);
        if(previous != null)
            throw new IllegalStateException("Second singleton " + this + " created after " + previous);
    }

    public static AuthenticationRepository getInstance() {
        return INSTANCE.get();
    }

    public String addCredential(final String token) {
        final String key = generateNewKey();
        credentials.put(key, token);
        return key;
    }

    public void validateUser(final String innerToken) {
        final String authorizationToken = getAuthorizationToken(innerToken);
        new FacebookTemplate(authorizationToken);
    }

    public User getUser(final String innerToken) {
        final String authorizationToken = getAuthorizationToken(innerToken);
        if(authorizationToken == null)
            throw new UserDataNotStoredException();
        try {
            final Facebook facebook = new FacebookTemplate(authorizationToken);
            return facebook.userOperations().getUserProfile();
        } catch (Exception ex) {
            if (ex instanceof InvalidAuthorizationException)
                credentials.remove(innerToken);
            throw ex;
        }
    }

    private String getAuthorizationToken(String innerToken) throws UserNotExist {
        final String authorizationToken = credentials.get(innerToken);
        if(authorizationToken == null)
            throw new UserDataNotStoredException();
        return authorizationToken;
    }


    private String generateNewKey() {
        return "" + counter++;
    }


}
