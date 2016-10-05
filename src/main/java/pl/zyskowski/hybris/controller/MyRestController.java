package pl.zyskowski.hybris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.web.bind.annotation.*;
import pl.zyskowski.hybris.access.AuthenticationRepository;
import pl.zyskowski.hybris.service.MovieLibraryFacade;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.OrderBy;

import javax.validation.Valid;
import java.util.Collection;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/movielibrary/rest")
public class MyRestController {

    @Autowired
    MovieLibraryFacade movieLibraryFacade;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @RequestMapping(value = "/get/{title}", method = RequestMethod.GET)
    public ResponseEntity<Movie> getById(@PathVariable String title) {
        try {
            final Movie movie = movieLibraryFacade.get(title);
            return new ResponseEntity(movie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<Collection<Movie>> getAll(@RequestParam(required = false) OrderBy order) {
        try {
            final Collection<Movie> movies = movieLibraryFacade.getAll(order);
            return new ResponseEntity(movies, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Movie> addMovie(@RequestHeader(name = "Bearer") String innerToken,
                                                        @Valid @RequestBody Movie movie)
    {
        try {
            User user = authenticationRepository.getUser(innerToken);
            final Movie addedMovie = movieLibraryFacade.add(user, movie);
            return new ResponseEntity(addedMovie, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update/{title}", method = RequestMethod.POST)
    public ResponseEntity<Movie> update(@RequestHeader(name = "Bearer") String innerToken,
                                        @PathVariable String title,
                                        @RequestBody @Valid Movie movie) {
        try {
            authenticationRepository.validateUser(innerToken);
            final Movie updatedMovie = movieLibraryFacade.update(title, movie);
            return new ResponseEntity(updatedMovie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{title}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@RequestHeader(name = "Bearer") String innerToken,
                                 @PathVariable String title) {
        try {
            User user = authenticationRepository.getUser(innerToken);
            movieLibraryFacade.remove(user, title);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/rate/{title}/{rating}", method = RequestMethod.POST)
    public ResponseEntity<Movie> rate(@RequestHeader(name = "Bearer") String innerToken,
                                      @PathVariable String title,
                                      @PathVariable Double rating) {
        try {
            final User user = authenticationRepository.getUser(innerToken);
            final Movie movie = movieLibraryFacade.rate(title, user, rating);
            return new ResponseEntity(movie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
