package solutions.thex.smoothy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class PayloadIsNotSatisfactoryException extends RuntimeException {

    public PayloadIsNotSatisfactoryException(String message) {
        super(message);
    }

}
