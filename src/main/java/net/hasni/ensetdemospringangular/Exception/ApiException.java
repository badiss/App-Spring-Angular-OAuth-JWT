package net.hasni.ensetdemospringangular.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.ZonedDateTime;

@Getter @Setter
public class ApiException {
    private final String message;
    private final int httpStatusCode;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String message, int httpStatusCode, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;

    }



}
