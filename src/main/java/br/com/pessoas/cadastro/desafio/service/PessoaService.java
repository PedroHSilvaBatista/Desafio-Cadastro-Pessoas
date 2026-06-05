package br.com.pessoas.cadastro.desafio.service;

import br.com.pessoas.cadastro.desafio.dto.PessoaRequest;
import br.com.pessoas.cadastro.desafio.dto.PessoaResponse;
import br.com.pessoas.cadastro.desafio.exceptions.CPFValidacaoException;
import br.com.pessoas.cadastro.desafio.exceptions.EmailValidacaoException;
import br.com.pessoas.cadastro.desafio.exceptions.LoginGeracaoException;
import br.com.pessoas.cadastro.desafio.mapper.PessoaMapper;
import br.com.pessoas.cadastro.desafio.model.Pessoa;
import br.com.pessoas.cadastro.desafio.repository.PessoaRepository;
import br.com.pessoas.cadastro.desafio.util.LoginGerador;
import br.com.pessoas.cadastro.desafio.util.NomeNormalizador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service responsável pelas regras de negócio do cadastro de pessoas.
 *
 * Fluxo de cadastro:
 * 1. Valida unicidade do CPF
 * 2. Valida unicidade do e-mail
 * 3. Normaliza o nome para geração do login
 * 4. Gera o login automaticamente
 * 5. Persiste a pessoa no banco de dados
 * 6. Retorna os dados cadastrados com o login gerado
 */
@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    /**
     * Realiza o cadastro de uma nova pessoa no sistema.
     *
     * @param request dados da pessoa enviados pelo front-end
     * @return resposta com os dados cadastrados e o login gerado
     * @throws CPFValidacaoException   se o CPF já estiver cadastrado
     * @throws EmailValidacaoException se o e-mail já estiver cadastrado
     * @throws LoginGeracaoException   se não for possível gerar um login único
     */
    public PessoaResponse cadastrarPessoa(PessoaRequest request) {

        // Verifica se já existe uma pessoa cadastrada com o mesmo CPF
        if (pessoaRepository.existsByDocumentoCpf(request.documentoCpf())) {
            throw new CPFValidacaoException("Já existe uma pessoa com o mesmo CPF cadastrado");
        } else if (pessoaRepository.existsByEmail(request.email())) {
            // Verifica se já existe uma pessoa cadastrada com o mesmo e-mail
            throw new EmailValidacaoException("Já existe uma pessoa com o mesmo Email cadastrado");
        }

        // Busca todos os logins existentes de uma vez para evitar múltiplas consultas ao banco
        Set<String> conjuntoLogins = new HashSet<>(pessoaRepository.findAllLogins());

        // Normaliza o nome removendo acentos e cedilha antes de gerar o login
        String nomeNormalizado = NomeNormalizador.normalizarNome(request.nomeCompleto());

        // Gera o login único a partir do nome normalizado
        String login = LoginGerador.gerarlogin(nomeNormalizado, conjuntoLogins);

        // Lança exceção se nenhuma combinação de login estiver disponível
        if (login.isEmpty()) {
            throw new LoginGeracaoException("Não foi possível gerar o nome de login!");
        }

        // Converte o request para entidade, define o login e persiste no banco
        Pessoa pessoa = PessoaMapper.toEntity(request);
        pessoa.setLogin(login);
        pessoaRepository.save(pessoa);

        return PessoaMapper.toResponse(pessoa);
    }
}
