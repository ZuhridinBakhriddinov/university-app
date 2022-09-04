package uz.project.app_university.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistCustomException extends RuntimeException{
    public AlreadyExistCustomException(String message) {
        super(message);
    }
}
