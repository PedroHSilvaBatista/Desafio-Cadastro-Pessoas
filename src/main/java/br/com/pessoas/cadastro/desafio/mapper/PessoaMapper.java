package br.com.pessoas.cadastro.desafio.mapper;

import br.com.pessoas.cadastro.desafio.dto.PessoaRequest;
import br.com.pessoas.cadastro.desafio.dto.PessoaResponse;
import br.com.pessoas.cadastro.desafio.model.Pessoa;

/**
 * Responsável pela conversão entre as camadas da aplicação.
 *
 * Centraliza o mapeamento entre DTOs e entidade, evitando que o Service
 * ou o Controller precisem conhecer os detalhes de construção dos objetos.
 *
 * Métodos estáticos — não necessita de instância pois não possui estado.
 */
public class PessoaMapper {

    /**
     * Converte um PessoaRequest (dados recebidos do front-end) para a entidade Pessoa.
     *
     * Observação: o campo login não é mapeado aqui pois é gerado
     * automaticamente pelo PessoaService após a construção da entidade.
     *
     * @param request dados validados recebidos pelo Controller
     * @return entidade Pessoa pronta para ser persistida
     */
    public static Pessoa toEntity(PessoaRequest request) {
        return Pessoa.builder()
                .nomeCompleto(request.nomeCompleto())
                .documentoCpf(request.documentoCpf())
                .email(request.email())
                .dataNascimento(request.dataNascimento())
                .cep(request.cep())
                .logradouro(request.logradouro())
                .complemento(request.complemento())
                .bairro(request.bairro())
                .cidade(request.cidade())
                .estado(request.estado())
                .build();
    }

    /**
     * Converte a entidade Pessoa para um PessoaResponse (dados retornados ao front-end).
     *
     * Inclui todos os campos cadastrados juntamente com o login gerado,
     * conforme exigido pelo case.
     *
     * @param pessoa entidade persistida no banco de dados
     * @return DTO de resposta com todos os dados cadastrados e o login gerado
     */
    public static PessoaResponse toResponse(Pessoa pessoa) {
        return PessoaResponse.builder()
                .id(pessoa.getId())
                .nomeCompleto(pessoa.getNomeCompleto())
                .documentoCpf(pessoa.getDocumentoCpf())
                .email(pessoa.getEmail())
                .dataNascimento(pessoa.getDataNascimento())
                .cep(pessoa.getCep())
                .logradouro(pessoa.getLogradouro())
                .complemento(pessoa.getComplemento())
                .bairro(pessoa.getBairro())
                .cidade(pessoa.getCidade())
                .estado(pessoa.getEstado())
                .login(pessoa.getLogin())
                .build();
    }
}
