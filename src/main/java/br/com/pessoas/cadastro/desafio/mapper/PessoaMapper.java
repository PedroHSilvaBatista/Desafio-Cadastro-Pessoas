package br.com.pessoas.cadastro.desafio.mapper;

import br.com.pessoas.cadastro.desafio.dto.PessoaRequest;
import br.com.pessoas.cadastro.desafio.dto.PessoaResponse;
import br.com.pessoas.cadastro.desafio.model.Pessoa;

public class PessoaMapper {

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
