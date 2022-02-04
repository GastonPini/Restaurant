package ar.com.flexia.restaurant.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.flexia.restaurant.api.dto.APIError;
import ar.com.flexia.restaurant.services.APIException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ APIException.class })
    public ResponseEntity<APIError> handleAPIException(APIException e, WebRequest request) {
        
        String message = e.getMessage();

        APIError error = new APIError(e.getCode(), message, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ AccessDeniedException.class, AuthenticationException.class })
    public ResponseEntity<APIError> handleAccessDenied(RuntimeException e, WebRequest request) {
    	APIError error = new APIError("access-denied", "Acceso inválido", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<APIError> handleMyException(Exception e, WebRequest request) {
        LOG.error("General Error", e);
        APIError error = new APIError("internal-error", e.getMessage(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}