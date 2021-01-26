package vn.com.ezmobi.ezhealth.ezhuserservice.web.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.DataNotFoundException;
import vn.com.ezmobi.ezhealth.ezhuserservice.services.exceptions.TaskExecutionException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ezmobivietnam on 2021-01-04.
 */
@Slf4j
@ControllerAdvice
public class MvcExceptionHandler {

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<List<String>> beanValidationError(ConstraintViolationException exc) {
//        log.error("Bean validation error", exc);
//        List<String> errorsList = new ArrayList<>(exc.getConstraintViolations().size());
//
//        exc.getConstraintViolations().forEach(error -> errorsList.add(error.toString()));
//
//        return ResponseEntity.badRequest().body(errorsList);
//    }
//

    /**
     * Method parameters that are decorated with the @PathVariable annotation can be of any simple type such as int,
     * long, Date... Spring automatically converts to the appropriate type and throws a TypeMismatchException if the
     * type is not correct.
     *
     * @param exc
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<String>> methodArgumentTypeMismatchError(MethodArgumentTypeMismatchException exc) {
        log.error("Method argument type mismatch error", exc);
        return ResponseEntity.badRequest().body(List.of(exc.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodValidationError(MethodArgumentNotValidException exc) {
        log.error("Method argument validation error", exc);
        Map<String, String> errors = new HashMap<>();
        exc.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(TaskExecutionException.class)
    public ResponseEntity<String> taskExecutionError(TaskExecutionException exc) {
        log.error("Execution Client requests error", exc);
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<HttpStatus> dataNotFoundError(DataNotFoundException exc) {
        log.error("Data not found error", exc);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ResponseEntity<List<String>> databaseError(Exception exc) {
        log.error("Database error", exc);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(exc.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<String>> internalServerError(Exception exc) {
        log.error("Unexpected error", exc);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(exc.getMessage()));
    }
}
