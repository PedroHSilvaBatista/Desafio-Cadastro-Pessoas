package br.com.pessoas.cadastro.desafio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pessoas")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private String documentoCpf;
    private String email;
    private LocalDate dataNascimento;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(length = 7)
    @Setter
    private String login;
}
