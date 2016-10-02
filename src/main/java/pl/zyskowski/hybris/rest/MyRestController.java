package pl.zyskowski.hybris.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.zyskowski.hybris.database.MongoDB;
import pl.zyskowski.hybris.movielibrary.facades.MovieLibraryFacade;
import pl.zyskowski.hybris.movielibrary.model.Movie;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.UnknownHostException;

@RestController
@RequestMapping("/movielibrary/rest")
public class MyRestController {

    @Autowired
    MovieLibraryFacade movieLibraryFacade;

    @RequestMapping(value = "/get/id/{objectId}", method = RequestMethod.GET)
    public String getById(@PathVariable String objectId) {
//        try {
//            MongoDB.getInstance().test();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        throw new NotImplementedException();
        return "abcd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        try {
            movieLibraryFacade.add(movie);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(movie, HttpStatus.OK);
    }

    @RequestMapping("/movie/get/{title}")
    public String getByTitle(@RequestParam String objectId) {
        throw new NotImplementedException();
    }

}
