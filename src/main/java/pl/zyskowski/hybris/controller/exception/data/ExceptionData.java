package pl.zyskowski.hybris.controller.exception.data;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.List;

public class ExceptionData {

    public String exception;
    public String message;

    public ExceptionData(Exception ex) {
        this.message = ex.getMessage();
        this.exception = ex.getClass().getName();
    }

}
