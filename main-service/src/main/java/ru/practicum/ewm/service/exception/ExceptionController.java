package ru.practicum.ewm.service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.dto.ApiError;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Incorrectly made request.",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(NumberFormatException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Incorrectly made request.",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Database integrity violation",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EventDateTimeException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(EventDateTimeException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ClosedCommentsException.class)
    public ResponseEntity<?> handleClosedCommentsException(ClosedCommentsException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Integrity constraint has been violated.",
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Incorrectly made request.",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<?> handleEventNotFoundException(EventNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IncorrectStateException.class)
    public ResponseEntity<?> handleIncorrectStateException(IncorrectStateException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(RequestConflictException.class)
    public ResponseEntity<?> handleIncorrectStateException(RequestConflictException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(RepeatedRequestException.class)
    public ResponseEntity<?> handleRepeatedRequestException(RepeatedRequestException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(LimitUserException.class)
    public ResponseEntity<?> handleLimitUserException(LimitUserException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    @ExceptionHandler(AccessEventException.class)
    public ResponseEntity<?> handleAccessUserException(AccessEventException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> handleCommentNotFoundException(CommentNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<?> handleRequestNotFoundException(RequestNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CompilationNotFoundException.class)
    public ResponseEntity<?> handleCompilationNotFoundException(CompilationNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AccessCommentException.class)
    public ResponseEntity<?> handleAccessCommentException(AccessCommentException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

}