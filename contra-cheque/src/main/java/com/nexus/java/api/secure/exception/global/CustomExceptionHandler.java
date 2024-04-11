package com.nexus.java.api.secure.exception.global;

import com.nexus.aws.exception.FileAlreadyExistsException;
import com.nexus.aws.exception.FileNotExists;
import com.nexus.aws.exception.FolderEmptyException;
import com.nexus.java.api.domain.exception.EmployeeNotAdminException;
import com.nexus.java.api.domain.exception.InvalidContentTypeException;
import com.nexus.java.api.domain.exception.InvalidPaycheckDateException;
import com.nexus.java.api.infrastructure.exceptions.EmployeeAlreadyException;
import com.nexus.java.api.infrastructure.exceptions.EmployeeNotFoundException;
import com.nexus.java.api.infrastructure.exceptions.EmptyEmployeeListException;
import com.nexus.java.api.secure.exception.Error;
import com.nexus.validations.exceptions.LargePasswordException;
import com.nexus.validations.exceptions.SmallPasswordException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;

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

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Error> handleValidationException(HttpServletRequest s) {
        return this.response("Funcionário não existe", HttpStatus.NOT_FOUND, s.getRequestURI());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Error> handleNullPointerException(NullPointerException e, HttpServletRequest s) {
        return this.response(e.getMessage(), HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> badRequestException(BadCredentialsException e, HttpServletRequest s) {
        return this.response(e.getMessage(), HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(SmallPasswordException.class)
    public ResponseEntity<Error> smallPasswordException(HttpServletRequest s) {
        return this.response("Senha inválida, use uma senha maior que 8 caracteres", HttpStatus.BAD_REQUEST, s.getRequestURI());
        }

    @ExceptionHandler(LargePasswordException.class)
    public ResponseEntity<Error> largePasswordException(HttpServletRequest s) {
        return this.response("Senha inválida, use uma senha menor que 100 caracteres", HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(EmployeeAlreadyException.class)
    public ResponseEntity<Error> userAlreadyExistsException(HttpServletRequest s) {
        return this.response("Usuário já existe", HttpStatus.CONFLICT, s.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentINvalidException(MethodArgumentNotValidException e, HttpServletRequest s) {
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return this.response(defaultMessage, HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(FolderEmptyException.class)
    public ResponseEntity<Error> folderEmptyException(FolderEmptyException e, HttpServletRequest s){
        return this.response(e.getMessage(), HttpStatus.BAD_REQUEST, s.getRequestURI());
    }

    @ExceptionHandler(EmployeeNotAdminException.class)
    public ResponseEntity<Error> userNotAdminException(HttpServletRequest s){
        return this.response("Usuário não é admin", HttpStatus.INTERNAL_SERVER_ERROR, s.getRequestURI());
    }

    @ExceptionHandler(EmptyEmployeeListException.class)
    public ResponseEntity<Error> emptyEmployeeListException(EmptyEmployeeListException e, HttpServletRequest s){
        return this.response(e.getMessage(), HttpStatus.NOT_FOUND, s.getRequestURI());
    }

    @ExceptionHandler(FileNotExists.class)
    public ResponseEntity<Error> fileNotExists(FileNotExists e, HttpServletRequest s){
        return this.response(e.getMessage(), HttpStatus.NOT_FOUND, s.getRequestURI());
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    public ResponseEntity<Error> fileAlreadyExistsException(FileAlreadyExistsException e, HttpServletRequest s){
        return this.response(e.getMessage(), HttpStatus.CONFLICT, s.getRequestURI());
    }

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<Error> invalidContentTypeException(InvalidContentTypeException e, HttpServletRequest s){
        return this.response(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, s.getRequestURI());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Error> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest s){
        return this.response("O método de requisição não está disponivel para esse endpoint" , HttpStatus.INTERNAL_SERVER_ERROR, s.getRequestURI());
    }

    @ExceptionHandler(InvalidPaycheckDateException.class)
    public ResponseEntity<Error> invalidPaycheckDateException(InvalidPaycheckDateException e, HttpServletRequest s){
        return this.response(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR, s.getRequestURI());
    }



}