package net.hasni.ensetdemospringangular.Exception;

public class ApiMethodeArgNotValidException extends RuntimeException {

    public ApiMethodeArgNotValidException(String message) {
        super(message);
    }

    public ApiMethodeArgNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
