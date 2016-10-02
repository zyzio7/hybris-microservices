package pl.zyskowski.hybris.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.zyskowski.hybris.movielibrary.facades.MovieLibraryFacade;
import pl.zyskowski.hybris.movielibrary.model.Movie;
import pl.zyskowski.hybris.movielibrary.utils.OrderBy;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/movielibrary/rest")
public class MyRestController {

    @Autowired
    MovieLibraryFacade movieLibraryFacade;

    @RequestMapping(value = "/get/{title}", method = RequestMethod.GET)
    public ResponseEntity<Movie> getById(@PathVariable String title) {
        try {
            final Movie movie = movieLibraryFacade.get(title);
            return new ResponseEntity(movie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Movie> addMovie(final BindingResult result, @RequestBody Movie movie) {
        if(result.hasErrors()) {
            return new ResponseEntity(result.getAllErrors().stream().map(e -> e.getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            final Movie addedMovie = movieLibraryFacade.add(movie);
            return new ResponseEntity(addedMovie, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update/{title}", method = RequestMethod.POST)
    public ResponseEntity<Movie> update(@PathVariable String title,
                                        @RequestBody @Valid Movie movie) {
        try {
            final Movie updatedMovie = movieLibraryFacade.update(title, movie);
            return new ResponseEntity(updatedMovie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{title}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String title) {
        try {
            movieLibraryFacade.remove(title);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get/category/{category}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Movie>> getByCategory(@RequestParam String category) {
        try {
            final Collection<Movie> movies = movieLibraryFacade.getByCategory(category);
            return new ResponseEntity(movies, HttpStatus.OK);
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

    @RequestMapping(value = "/rate/{title}/{rating}", method = RequestMethod.GET)
    public ResponseEntity<Movie> rate(@RequestParam String title, @RequestParam Double rating) {
        try {
            final Movie movie = movieLibraryFacade.rate(title, rating);
            return new ResponseEntity(movie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
