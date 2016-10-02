package pl.zyskowski.hybris.movielibrary.facades.impl;

import org.springframework.stereotype.Component;
import pl.zyskowski.hybris.movielibrary.facades.MovieLibraryFacade;
import pl.zyskowski.hybris.movielibrary.model.Movie;

@Component
public class DefaultMovieLibraryFacade implements MovieLibraryFacade {

    @Override
    public void add(Movie movie) throws Exception {
        Integer titleLength = movie.getTitle().length();
        if (titleLength < 3 || titleLength > 50)
            throw new Exception("Title length should be beetwin 3 and 50");

    }

    @Override
    public void remove(String title) {

    }

    @Override
    public void update(Movie movie) {

    }

    @Override
    public void rate(String title, Double rating) {

    }
}
