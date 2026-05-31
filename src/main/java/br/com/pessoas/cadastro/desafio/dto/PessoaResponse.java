package br.com.pessoas.cadastro.desafio.dto;

import br.com.pessoas.cadastro.desafio.model.Estado;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PessoaResponse(Long id,
         String nomeCompleto,
         String documentoCpf,
         String email,
         LocalDate dataNascimento,
         String cep,
         String logradouro,
         String complemento,
         String bairro,
         String cidade,
         Estado estado,
         String login) {
}
