package net.hasni.ensetdemospringangular.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Exception pour mouvaise requette: (BAD_REQUEST)
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException (ApiRequestException ex) {
        //créer une charge utile contenant les détails de l'exception
        ApiException apiException = new ApiException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // return response Entity
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception pour objet not Found (NOT_FOUND)
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleObjectNotFoundException (ApiNotFoundException ex) {
        //créer une charge utile contenant les détails de l'exception
        ApiException apiException = new ApiException(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // return response Entity
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ApiMethodeArgNotValidException.class})
    public ResponseEntity<Object> handleMethodeArgNotFoundException (ApiMethodeArgNotValidException ex) {
        //créer une charge utile contenant les détails de l'exception
        ApiException apiException = new ApiException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // return response Entity
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

}
