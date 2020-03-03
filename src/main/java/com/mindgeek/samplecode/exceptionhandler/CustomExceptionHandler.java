package com.mindgeek.samplecode.exceptionhandler;

import com.mindgeek.samplecode.datatransferobject.response.ApiError;
import com.mindgeek.samplecode.exception.AccessForbiddenException;
import com.mindgeek.samplecode.exception.ContentNotFoundException;
import com.mindgeek.samplecode.exception.DataValidationException;
import com.mindgeek.samplecode.exception.DuplicateRecordException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//    /**
//     * Manage DuplicateRecordException
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleDuplicateRecordException(
//            Exception ex) {
//        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
//        apiError.setMessage(ex.getCause().getCause().getMessage());
//        return buildResponseEntity(apiError);
//    }


    /**
     * Manage DuplicateRecordException
     * @param ex
     * @return
     */
    @ExceptionHandler(DuplicateRecordException.class)
    protected ResponseEntity<Object> handleDuplicateRecordException(
            DuplicateRecordException ex) {
        ApiError apiError = new ApiError(CONFLICT);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Manage DataValidationException
     * @param ex
     * @return
     */
    @ExceptionHandler(DataValidationException.class)
    protected ResponseEntity<Object> handleDataValidationException(
            DataValidationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Manage ContentNotFoundException
     * @param ex
     * @return
     */
    @ExceptionHandler(ContentNotFoundException.class)
    protected ResponseEntity<Object> handleContentNotFoundException(
            ContentNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Manage AccessForbiddenException
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessForbiddenException.class)
    protected ResponseEntity<Object> handleAccessForbiddenException(
            AccessForbiddenException ex) {
        ApiError apiError = new ApiError(FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatusType());
    }
}

