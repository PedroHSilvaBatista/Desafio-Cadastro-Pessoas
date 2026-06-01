package br.com.pessoas.cadastro.desafio.service;

import br.com.pessoas.cadastro.desafio.dto.PessoaRequest;
import br.com.pessoas.cadastro.desafio.dto.PessoaResponse;
import br.com.pessoas.cadastro.desafio.mapper.PessoaMapper;
import br.com.pessoas.cadastro.desafio.model.Pessoa;
import br.com.pessoas.cadastro.desafio.repository.PessoaRepository;
import br.com.pessoas.cadastro.desafio.util.LoginGerador;
import br.com.pessoas.cadastro.desafio.util.NomeNormalizador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;


    public PessoaResponse cadastrarPessoa(PessoaRequest request) {

        if (pessoaRepository.existsByDocumentoCpf(request.documentoCpf())) {
            throw new RuntimeException("Já existe uma pessoa com o mesmo CPF cadastrado"); // Criar posteriormente uma exceção personalizada
        } else if (pessoaRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Já existe uma pessoa com o mesmo Email cadastrado"); // Criar posteriormente uma exceção personalizada
        }

        Set<String> conjuntoLogins = new HashSet<>(pessoaRepository.findAllLogins());
        String nomeNormalizado = NomeNormalizador.normalizarNome(request.nomeCompleto());
        String login = LoginGerador.gerarlogin(nomeNormalizado, conjuntoLogins);

        if (login.isEmpty()) {
            throw new RuntimeException("Não foi gerar o nome de login!"); // Criar posteriormente uma exceção personalizada
        }

        Pessoa pessoa = PessoaMapper.toEntity(request);
        pessoa.setLogin(login);

        pessoaRepository.save(pessoa);
        return PessoaMapper.toResponse(pessoa);
    }

    public List<String> exibirTodosLogins() {
        return pessoaRepository.findAllLogins();
    }
}
