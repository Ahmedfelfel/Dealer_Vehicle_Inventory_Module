package com.felfel.dealer_vehicle_inventory_module.system.exception;

import com.felfel.dealer_vehicle_inventory_module.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AccountStatusException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Exception handler advise.
 */
@RestControllerAdvice
public class ExceptionHandlerAdvise {

    /**
     * Handle object not found exception result.
     *
     * @param ex the ex
     * @return the result
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleObjectNotFoundException(ObjectNotFoundException ex)
    {
        return new Result(false, HttpStatus.NOT_FOUND.value(),ex.getMessage());
    }

    /**
     * Handle validation exception result.
     *
     * @param ex the ex
     * @return the result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException ex)
    {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String,String> map = new HashMap<>(errors.size());
        errors.forEach((error)->{
            String key = ((FieldError) error).getField();
            String value = error.getDefaultMessage();
            map.put(key,value);
        });

        return new Result(false,
                                HttpStatus.BAD_REQUEST.value(),
                        "Provided arguments are invalid",
                                map);
    }

    /**
     * Handle access denied exception result.
     *
     * @param ex the ex
     * @return the result
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    Result handleAccessDeniedException(AccessDeniedException ex)
    {
        return new Result(false, HttpStatus.FORBIDDEN.value(),"no permission",ex.getMessage());
    }

    /**
     * Handle other exception result.
     *
     * @param ex the ex
     * @return the result
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Result handleOtherException(Exception ex)
    {
        return new Result(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),"Internal server error.",ex.getMessage());
    }

}
