package vn.nuce.ducnh.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.exception.ErrorResponse;
import vn.nuce.ducnh.exception.ExceptionMessage;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@CommonsLog
public class DucnhControllerAdvice {
    private MessageSource messageSource;
    private Environment environment;

    private boolean debugEnable = false;

    public DucnhControllerAdvice(MessageSource messageSource, Environment environment) {
        this.messageSource = messageSource;
        this.environment = environment;
    }

    @PostConstruct
    private void init() {
        try {
            Boolean debug = environment.getProperty("debug", Boolean.class);
            if (debug != null) {
                debugEnable = debug;
            } else {
                if (environment.containsProperty("debug")) {
                    debugEnable = true;
                }
            }
        } catch (Exception e) {
            log.error("check debug info has error: " + e.getMessage());
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler({DucnhException.class})
    public ResponseEntity<ErrorResponse> handleDucnhVException(DucnhException ex, WebRequest request) {
        logError(ex.getResponseStatusCode(), ex, ex.getErrors());

        ErrorResponse res = new ErrorResponse(ex);

        return new ResponseEntity<>(res, ex.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionMessage processValidationError(MethodArgumentNotValidException ex, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ExceptionMessage> exMessages = fieldErrors
                .stream()
                .map(fieldError -> new ExceptionMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        ExceptionMessage exceptionMessage = exMessages.get(0);
        return exceptionMessage;
    }


    private void logError(String responseStatusCode, Throwable ex, List<ObjectError> errors) {
        if (debugEnable) {
            log.error(responseStatusCode, ex);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(responseStatusCode).append(" - ").append(ex.getMessage());

            if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
                StackTraceElement e = ex.getStackTrace()[0];
                builder.append(" @").append(e.getClassName()).append(".").append(e.getMethodName()).append(":").append(e.getLineNumber());
            }


            log.error(builder.toString());
        }

        if (errors != null) {
            log.error(errors);
        }
    }
}
