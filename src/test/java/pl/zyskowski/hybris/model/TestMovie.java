package pl.zyskowski.hybris.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMovie {

    private Movie movie;

    @Before
    public void setUp() {
        movie = new Movie();
    }

    private Boolean equalWithPrecision(final Double val1, final Double val2) {
        Double precision = 0.01;
        if (val1 < (val2 + precision) && val1 > (val2 - precision))
            return true;
        else
            return false;
    }

    @Test
    public void averageRatingShouldBeCalculated() {
        movie.addRating(new UserModel("user1", "username1"), 3);
        movie.addRating(new UserModel("user2", "username2"), 5);
        assert(equalWithPrecision(movie.getAverageRating(), 4.0));
        movie.addRating(new UserModel("user3", "username3"), 7);
        assert(equalWithPrecision(movie.getAverageRating(), 5.0));
    }

    @Test
    public void sameUserShouldUpdateHisRateIfAlreadyRated() {
        movie.addRating(new UserModel("user1", "username1"), 3);
        assert(equalWithPrecision(movie.getAverageRating(), 3.0));
        movie.addRating(new UserModel("user1", "username1"),  5);
        assert(equalWithPrecision(movie.getAverageRating(), 5.0));
    }

    @Test
    public void averageRatingShouldBeUpdatedAfterUserRateChange() {
        movie.addRating(new UserModel("user1", "username1"), 3);
        movie.addRating(new UserModel("user2", "username1"), 7);
        assert(equalWithPrecision(movie.getAverageRating(), 5.0));
        movie.addRating(new UserModel("user1", "username1"), 5);
        assert(equalWithPrecision(movie.getAverageRating(), 6.0));
    }

}
