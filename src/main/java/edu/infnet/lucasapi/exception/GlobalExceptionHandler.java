package edu.infnet.lucasapi.exception;

import edu.infnet.lucasapi.controller.dto.response.ApiResponseDto;
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

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            PetException.class,
            UsuarioException.class,
            AvistamentoException.class
    })
    public ResponseEntity<ApiResponseDto<Void>> handleDomainExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.fail(ex.getMessage()));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.fail(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação nos campos da requisição.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.fail(mensagem));
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleConstraintErrors(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.fail("Violação de integridade no banco de dados."));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.fail("Erro inesperado: " + ex.getMessage()));
    }
}
