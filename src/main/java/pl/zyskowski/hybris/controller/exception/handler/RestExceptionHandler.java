package pl.zyskowski.hybris.controller.exception.handler;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.zyskowski.hybris.controller.exception.custom.authorization.*;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieAlreadyExist;
import pl.zyskowski.hybris.controller.exception.custom.resource.MovieNotFoundException;
import pl.zyskowski.hybris.controller.exception.custom.resource.EmptyMovieTitleException;
import pl.zyskowski.hybris.controller.exception.data.ExceptionData;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
        return new ResponseEntity(new ExceptionData(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { UserDataNotStoredException.class, UserNotExist.class, UserTokenHasExpired.class, FacebookAuthError.class, UserTokenHasExpired.class } )
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        return new ResponseEntity<>(new ExceptionData(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = DeleteNotOwnedMovieException.class)
    public ResponseEntity<Object> handleDeleteNotOwnedMovieException(Exception ex) {
        return new ResponseEntity<>(new ExceptionData(ex), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = MovieAlreadyExist.class)
    public ResponseEntity<Object> handleMovieAlreadyExistException(Exception ex){
        return new ResponseEntity<>(new ExceptionData(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = MovieNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception ex){
        return new ResponseEntity<>(new ExceptionData(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EmptyMovieTitleException.class)
    public ResponseEntity<Object> handleEmptyMovieTitleException(Exception ex){
        return new ResponseEntity<>(new ExceptionData(ex), HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionData(ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> messages = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(new ExceptionData(ex.getClass(), messages), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingPathVariable(ex, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionData(ex), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionData(ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionData(ex), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ExceptionData(ex), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ExceptionData(ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ExceptionData(ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ExceptionData(ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ExceptionData(ex), status);
    }
}
