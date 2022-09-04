package uz.project.app_university.exception;

public class CannotDeleteException extends RuntimeException {
    public CannotDeleteException(String message) {
        super(message);
    }
}
