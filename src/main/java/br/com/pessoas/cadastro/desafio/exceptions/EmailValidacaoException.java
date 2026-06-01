package br.com.pessoas.cadastro.desafio.exceptions;

public class EmailValidacaoException extends RuntimeException {
    public EmailValidacaoException(String mensagem) {
        super(mensagem);
    }
}
