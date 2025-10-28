package edu.infnet.lucasapi.api.handler;

import edu.infnet.lucasapi.api.response.ApiResponse;
import edu.infnet.lucasapi.domain.exception.AvistamentoException;
import edu.infnet.lucasapi.domain.exception.PetException;
import edu.infnet.lucasapi.domain.exception.UsuarioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            PetException.class,
            UsuarioException.class,
            AvistamentoException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleDomainExceptions(RuntimeException ex) {
        List<String> messages = null;

        if (ex instanceof PetException petEx)
            messages = petEx.getMessages();
        else if (ex instanceof UsuarioException usuEx)
            messages = usuEx.getMessages();
        else if (ex instanceof AvistamentoException aviEx)
            messages = aviEx.getMessages();

        if (messages == null || messages.isEmpty())
            messages = Collections.singletonList(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(messages));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> mensagens = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(mensagens));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintErrors(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail("Violação de integridade no banco de dados."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Erro inesperado: " + ex.getMessage()));
    }
}
