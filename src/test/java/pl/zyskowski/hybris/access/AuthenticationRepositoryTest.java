package pl.zyskowski.hybris.access;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.test.context.junit4.SpringRunner;
import pl.zyskowski.hybris.controller.exception.custom.authorization.UserDataNotStoredException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationRepositoryTest {

    @Spy
    @Autowired
    AuthenticationRepository authenticationRepository;

    @Test(expected = InvalidAuthorizationException.class)
    public void fakeTokenShouldThrowException() {
        String testToken = "123456";
        String innerToken = authenticationRepository.addCredential("123456");
        authenticationRepository.getUser(innerToken);
    }

    @Test(expected = UserDataNotStoredException.class)
    public void notAddedTokenShouldThrowException() {
        String testToken = "898989";
        authenticationRepository.getUser(testToken);
    }

}
