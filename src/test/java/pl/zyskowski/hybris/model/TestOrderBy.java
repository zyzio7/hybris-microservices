package pl.zyskowski.hybris.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOrderBy {

    @Test
    public void everyEnumToStringShouldBeSameAsFieldOfMovie() {
        List<String> movieFieldsNames = Stream.of(Movie.class.getDeclaredFields()).map(e -> e.getName()).collect(Collectors.toList());
        for(OrderBy orderBy : OrderBy.values()) {
            assertTrue(movieFieldsNames.contains(orderBy.toString()));
        }
    }

}
