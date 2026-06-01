package br.com.pessoas.cadastro.desafio.error;

import br.com.pessoas.cadastro.desafio.exceptions.CPFValidacaoException;
import br.com.pessoas.cadastro.desafio.exceptions.EmailValidacaoException;
import br.com.pessoas.cadastro.desafio.exceptions.LoginGeracaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex) {

        List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> fieldError = new HashMap<>();
                    fieldError.put("campo", error.getField());
                    fieldError.put("mensagem", error.getDefaultMessage());
                    return fieldError;
                }).toList();

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Erro de validação");
        pd.setDetail("Um ou mais campos estão inválidos");
        pd.setProperty("Timestamp", OffsetDateTime.now());
        pd.setProperty("Details", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(CPFValidacaoException.class)
    public ResponseEntity<ProblemDetail> handleValidacaoCPF(CPFValidacaoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("O CPF inserido já foi cadastrado");
        pd.setProperty("Timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(EmailValidacaoException.class)
    public ResponseEntity<ProblemDetail> handleValidacaoEmail(EmailValidacaoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("O Email inserido já foi cadastrado");
        pd.setProperty("Timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(LoginGeracaoException.class)
    public ResponseEntity<ProblemDetail> handleLoginException(LoginGeracaoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        pd.setTitle("Houve um erro na geração do login de usuário");
        pd.setProperty("Timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }
}
