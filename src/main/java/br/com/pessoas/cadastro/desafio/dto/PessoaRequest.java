package br.com.pessoas.cadastro.desafio.dto;

import br.com.pessoas.cadastro.desafio.model.Estado;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PessoaRequest(@NotBlank(message = "O campo 'nome' precisa ser preenchido") String nomeCompleto,
                            @NotBlank(message = "O campo 'CPF' precisa ser preenchido") @Size(min = 14, max = 14, message = "CPF deve estar no formato 123.456.789-09") String documentoCpf,
                            @NotBlank(message = "O campo 'email' precisa ser preenchido") @Email String email,
                            @NotNull(message = "O campo 'dataNascimento' precisa ser preenchido") @PastOrPresent(message = "A data de nascimento não pode ser futura") LocalDate dataNascimento,
                            @NotBlank(message = "O campo 'cep' precisa ser preenchido") @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 00000-000") String cep,
                            @NotBlank(message = "O campo 'logradouro' precisa ser preenchido") String logradouro,
                            String complemento,
                            @NotBlank(message = "O campo 'bairro' precisa ser preenchido") String bairro,
                            @NotBlank(message = "O campo 'cidade' precisa ser preenchido") String cidade,
                            @NotNull(message = "O campo 'estado' precisa ser preenchido") Estado estado) {
}
