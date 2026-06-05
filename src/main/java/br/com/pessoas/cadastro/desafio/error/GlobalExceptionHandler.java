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

/**
 * Interceptador global de exceções da aplicação.
 *
 * Captura exceções lançadas em qualquer camada e as converte em respostas
 * HTTP padronizadas no formato RFC 7807 (ProblemDetail), garantindo
 * consistência nas respostas de erro da API.
 *
 * Exceções tratadas:
 * - MethodArgumentNotValidException — erros de validação do Bean Validation
 * - CPFValidacaoException           — CPF já cadastrado
 * - EmailValidacaoException         — e-mail já cadastrado
 * - LoginGeracaoException           — falha na geração do login
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata erros de validação disparados pelo Bean Validation (@Valid).
     * Retorna a lista detalhada de todos os campos inválidos com suas mensagens.
     *
     * @param ex exceção contendo os erros de validação por campo
     * @return 400 Bad Request com detalhes dos campos inválidos
     */
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

    /**
     * Trata tentativa de cadastro com CPF já existente no sistema.
     *
     * @param ex exceção com a mensagem de CPF duplicado
     * @return 400 Bad Request informando que o CPF já está cadastrado
     */
    @ExceptionHandler(CPFValidacaoException.class)
    public ResponseEntity<ProblemDetail> handleValidacaoCPF(CPFValidacaoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("O CPF inserido já foi cadastrado");
        pd.setProperty("Timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    /**
     * Trata tentativa de cadastro com e-mail já existente no sistema.
     *
     * @param ex exceção com a mensagem de e-mail duplicado
     * @return 400 Bad Request informando que o e-mail já está cadastrado
     */
    @ExceptionHandler(EmailValidacaoException.class)
    public ResponseEntity<ProblemDetail> handleValidacaoEmail(EmailValidacaoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("O Email inserido já foi cadastrado");
        pd.setProperty("Timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    /**
     * Trata falha interna na geração do login.
     * Ocorre quando todas as combinações possíveis de login já estão em uso.
     *
     * @param ex exceção com a mensagem de falha na geração
     * @return 500 Internal Server Error indicando falha no processamento interno
     */
    @ExceptionHandler(LoginGeracaoException.class)
    public ResponseEntity<ProblemDetail> handleLoginException(LoginGeracaoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        pd.setTitle("Houve um erro na geração do login de usuário");
        pd.setProperty("Timestamp", OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }
}
