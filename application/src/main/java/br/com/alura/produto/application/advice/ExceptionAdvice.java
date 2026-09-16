package br.com.alura.produto.application.advice;

import br.com.alura.produto.domain.exception.BusinessException;
import br.com.alura.produto.domain.exception.ErroInternoException;
import br.com.alura.produto.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public String handle(MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        var builder = new StringBuilder();

        var fieldErrors = e.getBindingResult().getFieldErrors();
        if (!fieldErrors.isEmpty())
            fieldErrors.forEach(fieldError -> {
                var message = format("[%s] %s", fieldError.getField(), fieldError.getDefaultMessage());
                builder.append(message);
            });
        else
            e.getAllErrors().forEach(error -> builder.append(error.getDefaultMessage()));

        return builder.toString();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public String handle(MethodArgumentTypeMismatchException e) {
        log.warn(e.getMessage());
        return format("[%s] %s", e.getPropertyName(), e.getCause().getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public String handle(MissingRequestHeaderException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public String handle(MissingServletRequestParameterException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public String handle(HttpRequestMethodNotSupportedException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public String handle(HttpMessageNotReadableException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public String handle(HttpMediaTypeNotSupportedException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public String handle(NotFoundException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = UNPROCESSABLE_ENTITY)
    public String handle(BusinessException e) {
        if (e.getCause() != null)
            log.warn(e.getMessage(), e);
        else
            log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(ErroInternoException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public String handle(ErroInternoException e) {
        log.error(e.getMessage(), e);
        return "Internal error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public String handle(Exception e) {
        log.error(e.getMessage(), e);
        return "Internal error";
    }
}
