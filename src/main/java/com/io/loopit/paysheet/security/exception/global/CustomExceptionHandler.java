package com.io.loopit.paysheet.security.exception.global;

import com.io.loopit.paysheet.security.exception.Error;
import com.nexus.aws.exception.FileAlreadyExistsException;
import com.nexus.aws.exception.FileNotExists;
import com.nexus.aws.exception.FolderEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;

@Slf4j
@RestControllerAdvice
@SuppressWarnings("unused")
public class CustomExceptionHandler {


    private final Instant instant = Instant.now();

    private ResponseEntity<Error> response(String message, HttpStatus status, String uri){
        return ResponseEntity
                .status(status)
                .body(Error.builder()
                .timestamp(instant)
                .message(message)
                .status(status.value())
                .path(uri)
                .build());
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Error> handleNullPointerException(NullPointerException e, HttpServletRequest s) {
        log.error("method=NullPointerException | message: {}", e.getMessage());
        return this.response(e.getMessage(), HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> badRequestException(BadCredentialsException e, HttpServletRequest s) {
        log.error("method=BadCredentialsException | message: {}", e.getMessage());
        return this.response(e.getMessage(), HttpStatus.BAD_REQUEST, s.getRequestURI());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentInvalidException(MethodArgumentNotValidException e, HttpServletRequest s) {
        log.error("method=MethodArgumentNotValidException | message: {}", e.getMessage());
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return this.response(defaultMessage, HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(FolderEmptyException.class)
    public ResponseEntity<Error> folderEmptyException(FolderEmptyException e, HttpServletRequest s){
        log.error("method=FolderEmptyException | message: {}", e.getMessage());
        return this.response(e.getMessage(), HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(FileNotExists.class)
    public ResponseEntity<Error> fileNotExists(FileNotExists e, HttpServletRequest s){
        log.error("method=FileNotExists | message: {}", e.getMessage());
        return this.response(e.getMessage(), HttpStatus.NOT_FOUND, s.getRequestURI());
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    public ResponseEntity<Error> fileAlreadyExistsException(FileAlreadyExistsException e, HttpServletRequest s){
        log.error("method=FileAlreadyExistsException | message: {}", e.getMessage());
        return this.response(e.getMessage(), HttpStatus.CONFLICT, s.getRequestURI());
    }

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<Error> invalidContentTypeException(InvalidContentTypeException e, HttpServletRequest s){
        log.error("method=InvalidContentTypeException | message: {}", e.getMessage());
        return this.response(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, s.getRequestURI());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Error> httpRequestMethodNotSupportedException(HttpServletRequest s){
        String message = "O método de requisição não está disponivel para esse endpoint";
        log.error("method=HttpRequestMethodNotSupportedException | message: {}", message);
        return this.response( message, HttpStatus.INTERNAL_SERVER_ERROR, s.getRequestURI());
    }

}