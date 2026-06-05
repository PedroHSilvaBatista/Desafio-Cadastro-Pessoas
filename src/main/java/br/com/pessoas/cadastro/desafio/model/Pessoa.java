package br.com.pessoas.cadastro.desafio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidade que representa uma pessoa cadastrada no sistema.
 *
 * Mapeada para a tabela "pessoas" no banco de dados PostgreSQL.
 * As restrições de integridade (NOT NULL, UNIQUE) são gerenciadas
 * pelo Flyway via migration SQL, não por anotações na entidade.
 *
 * Anotações Lombok utilizadas:
 * - @Getter         — gera getters para todos os campos
 * - @Builder        — permite construção fluente via PessoaMapper
 * - @NoArgsConstructor — construtor vazio exigido pelo JPA
 * - @AllArgsConstructor — construtor completo exigido pelo @Builder
 */
@Entity
@Table(name = "pessoas")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    /** Identificador único gerado automaticamente pelo banco */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome completo da pessoa, normalizado sem acentos ou cedilha */
    private String nomeCompleto;

    /** CPF no formato 123.456.789-09 */
    private String documentoCpf;

    /** E-mail válido e único no sistema */
    private String email;

    /** Data de nascimento — não pode ser futura */
    private LocalDate dataNascimento;

    /** CEP no formato 00000-000 */
    private String cep;

    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;

    /**
     * Estado brasileiro representado como enum.
     * Persistido como String (sigla) no banco — ex: "SP", "RJ".
     * Usar EnumType.STRING evita quebras caso a ordem do enum mude.
     */
    @Enumerated(EnumType.STRING)
    private Estado estado;

    /**
     * Login gerado automaticamente pelo sistema com exatamente 7 caracteres.
     * É o único campo com @Setter pois é definido após a construção da entidade,
     * no PessoaService, antes de persistir.
     */
    @Column(length = 7)
    @Setter
    private String login;
}
