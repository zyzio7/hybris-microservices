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
        movie.addRating("user1", 3);
        movie.addRating("user2", 5);
        assert(equalWithPrecision(movie.getAverageRating(), 4.0));
        movie.addRating("user3", 7);
        assert(equalWithPrecision(movie.getAverageRating(), 5.0));
    }

    @Test
    public void sameUserShouldUpdateHisRateIfAlreadyRated() {
        movie.addRating("user1", 3);
        assert(equalWithPrecision(movie.getAverageRating(), 3.0));
        movie.addRating("user1", 5);
        assert(equalWithPrecision(movie.getAverageRating(), 5.0));
    }

    @Test
    public void averageRatingShouldBeUpdatedAfterUserRateChange() {
        movie.addRating("user1", 3);
        movie.addRating("user2", 7);
        assert(equalWithPrecision(movie.getAverageRating(), 5.0));
        movie.addRating("user1", 5);
        assert(equalWithPrecision(movie.getAverageRating(), 6.0));
    }

}
