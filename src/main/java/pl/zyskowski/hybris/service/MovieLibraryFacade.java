package pl.zyskowski.hybris.service;

import org.springframework.social.facebook.api.User;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.OrderBy;
import pl.zyskowski.hybris.model.UserModel;

import java.util.List;

public interface MovieLibraryFacade {

    Movie add(final UserModel user, final Movie movie);
    void remove(final UserModel user, final String title) throws Exception;
    Movie update(final String title, final Movie movie) throws Exception;
    Movie rate(final String title, UserModel user, Integer rating);
    Movie get(final String title);
    List<Movie> getAll();
    List<Movie> getAll(final OrderBy orderBy);

}
