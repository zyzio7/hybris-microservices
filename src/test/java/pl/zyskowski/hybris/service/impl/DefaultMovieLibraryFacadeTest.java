package pl.zyskowski.hybris.service.impl;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.social.facebook.api.User;
import org.springframework.test.context.junit4.SpringRunner;
import pl.zyskowski.hybris.controller.exception.custom.authorization.DeleteNotOwnedMovieException;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieAlreadyExist;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieNotFoundException;
import pl.zyskowski.hybris.database.MoviesDAO;
import pl.zyskowski.hybris.model.Category;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultMovieLibraryFacadeTest {

    @Spy
    @Autowired
    DefaultMovieLibraryFacade movieLibraryFacade;

    @Mock
    private MoviesDAO mockedDAO;

    private User existingUser;
    private User existingUser2;
    private Movie existingMovie;

    @Before
    public void setup() throws ParseException {
        //mocked dao
        when((movieLibraryFacade).getDao()).thenReturn(mockedDAO);

        //user1
        existingUser = new User("11111", "Name1", "Username1", "Lastname1", "male", Locale.ENGLISH);
        existingUser2 = new User("22222", "Name2", "Username2", "Lastname2", "male", Locale.ENGLISH);

        //existing movie
        existingMovie = new Movie();
        existingMovie.setTitle("ExistingMovieTitle");
        existingMovie.setCategory(Category.THRILLER);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("01/01/2010");
        existingMovie.setCreatedAt(date);
        existingMovie.setAddedBy(new UserModel(existingUser));
    }

    @Test(expected = MovieNotFoundException.class)
    public void updateNotExistMovieShouldThrowException() throws Exception {
        when(mockedDAO.getMovie(isA(String.class))).thenReturn(null);

        final Movie movie = new Movie();
        movie.setTitle("Not stored movie");
        movieLibraryFacade.update(movie.getTitle(), movie);
    }

    @Test
    public void movieShouldBeUpdated() throws Exception {

        when(movieLibraryFacade.getDao()).thenReturn(mockedDAO).thenReturn(mockedDAO);
        //return parameter
        when(mockedDAO.updateMovie(isA(Movie.class))).thenAnswer(new Answer<Movie>() {
            @Override
            public Movie answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Movie) args[0];
            }
        });
        when(mockedDAO.getMovie(isA(String.class))).thenReturn(existingMovie);

        final Movie movie = new Movie();

        //should not be stored
        movie.setId(new ObjectId());
        movie.setAddedBy(new UserModel(existingUser2));

        //should be stored
        movie.setTitle("New title");
        List<String> actors = Arrays.asList("Actor1", "Actor2");
        movie.setActors(actors);
        movie.setCategory(Category.THRILLER);

        //not every field should be updated
        final Movie updatedMovie = movieLibraryFacade.update(existingMovie.getTitle(), movie);

        verify(mockedDAO).getMovie(anyString());
        verify(mockedDAO).updateMovie(any(Movie.class));

        assert !movie.getId().equals(updatedMovie.getId());
        assert !movie.getAddedBy().equals(updatedMovie.getAddedBy());
        assert movie.getTitle().equals(updatedMovie.getTitle());
        assert movie.getActors().equals(updatedMovie.getActors());
        assert movie.getCategory().equals(updatedMovie.getCategory());
    }

    @Test(expected = MovieNotFoundException.class)
    public void removeNotExistingMovieShouldThrowException() {
        doThrow(new MovieNotFoundException("movie")).when(mockedDAO).remove(any(), eq("NotExistingMovie"));
        movieLibraryFacade.remove(existingUser, "NotExistingMovie");
        verify(mockedDAO).remove(any(), any());
    }

    @Test(expected = DeleteNotOwnedMovieException.class)
    public void removeNotOwnedMovieShouldThrowException() {
        doThrow(new DeleteNotOwnedMovieException(existingMovie)).when(mockedDAO).remove(any(), any());
        movieLibraryFacade.remove(existingUser2, "existingMovie");
        verify(mockedDAO).remove(any(), eq("existingMovie"));
    }

    @Test
    public void movieShouldBeRemoved() {
        movieLibraryFacade.remove(existingUser, "Title");
        verify(mockedDAO).remove(any(), eq("Title"));

        Movie movie = movieLibraryFacade.get("Title");
        assert(movie == null);
    }

    @Test
    public void movieShouldBeAdded() {
        //return parameter
        when(mockedDAO.addMovie(isA(Movie.class))).thenAnswer(new Answer<Movie>() {
            @Override
            public Movie answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Movie) args[0];
            }
        });

        Movie movieToAdd = new Movie();
        movieToAdd.setTitle("New title");
        movieLibraryFacade.add(existingUser, movieToAdd);
        verify(movieLibraryFacade).add(eq(existingUser), eq(movieToAdd));
        assert(movieToAdd.getAddedBy().equals(existingUser.getId()));
    }

    @Test(expected = MovieAlreadyExist.class)
    public void addingExistingMovieShouldThrowException() {
        when(mockedDAO.addMovie(eq(existingMovie))).thenThrow(new MovieAlreadyExist(existingMovie));
        movieLibraryFacade.add(existingUser, existingMovie);
    }

}
