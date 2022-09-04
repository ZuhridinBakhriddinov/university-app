package uz.project.app_university.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.project.app_university.common.ApiResponse;
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = AlreadyExistCustomException.class)
    public ResponseEntity<Object> alreadyExistException(AlreadyExistCustomException exception) {
        return ResponseEntity.badRequest().body(new ApiResponse("error",false,exception.getMessage()));
    }

    @ExceptionHandler(value = NotFoundSomethingException.class)
    public ResponseEntity<Object> notFoundException(NotFoundSomethingException exception) {
        return ResponseEntity.badRequest().body(new ApiResponse("error",false,exception.getMessage()));
    }

    @ExceptionHandler(value = CannotDeleteException.class)
    public ResponseEntity<Object> cannotDelete(CannotDeleteException exception) {
        return ResponseEntity.badRequest().body(new ApiResponse("error",false,exception.getMessage()));
    }
}
