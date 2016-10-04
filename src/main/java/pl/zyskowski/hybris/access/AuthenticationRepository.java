package pl.zyskowski.hybris.access;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

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

    public User getUser(final String innerToken) throws Exception {
        final String authorizationToken = credentials.get(innerToken);
        if(authorizationToken == null)
            throw new Exception("User data not stored");

        Facebook facebook = new FacebookTemplate(authorizationToken);
        return facebook.userOperations().getUserProfile();
    }

    private String generateNewKey() {
        return "" + counter++;
    }


}
