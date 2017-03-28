package pl.zyskowski.hybris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zyskowski.hybris.access.AuthenticationRepository;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieNotFoundException;
import pl.zyskowski.hybris.model.UserModel;
import pl.zyskowski.hybris.service.MovieLibraryFacade;
import pl.zyskowski.hybris.model.Movie;
import pl.zyskowski.hybris.model.OrderBy;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/movielibrary/rest")
@Controller
public class MyRestController {

    @Autowired
    MovieLibraryFacade movieLibraryFacade;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @RequestMapping(value = "/get/{title}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable String title) throws MovieNotFoundException {
        final Movie movie = movieLibraryFacade.get(title);
        if (movie == null)
            throw new MovieNotFoundException(title);
        else
            return new ResponseEntity(movie, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestParam(required = false) OrderBy order) {
        final Collection<Movie> movies = movieLibraryFacade.getAll(order);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity addMovie(@RequestHeader(name = "Bearer") String innerToken,
                                                        @Valid @RequestBody Movie movie)
    {
        UserModel user = authenticationRepository.getUser(innerToken);
        final Movie addedMovie = movieLibraryFacade.add(user, movie);
        return new ResponseEntity<>(addedMovie, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{title}", method = RequestMethod.POST)
    public ResponseEntity update(@RequestHeader(name = "Bearer") String innerToken,
                                        @PathVariable String title,
                                        @RequestBody @Valid Movie movie) throws Exception {
        authenticationRepository.validateUser(innerToken);
        final Movie updatedMovie = movieLibraryFacade.update(title, movie);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);

    }

    @RequestMapping(value = "/delete/{title}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@RequestHeader(name = "Bearer") String innerToken,
                                 @PathVariable String title) throws Exception {
        UserModel user = authenticationRepository.getUser(innerToken);
        movieLibraryFacade.remove(user, title);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rate/{title}/{rating}", method = RequestMethod.POST)
    public ResponseEntity rate(@RequestHeader(name = "Bearer") String innerToken,
                                      @PathVariable String title,
                                      @PathVariable Integer rating) {
        final UserModel user = authenticationRepository.getUser(innerToken);
        final Movie movie = movieLibraryFacade.rate(title, user, rating);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

}
