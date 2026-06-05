package br.com.pessoas.cadastro.desafio.controller;

import br.com.pessoas.cadastro.desafio.dto.PessoaRequest;
import br.com.pessoas.cadastro.desafio.dto.PessoaResponse;
import br.com.pessoas.cadastro.desafio.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controller responsável por expor os endpoints REST de cadastro de pessoas.
 *
 * Versionamento da API via prefixo /api/v1 — permite evoluir a API
 * sem quebrar integrações existentes caso novas versões sejam necessárias.
 */
@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    /**
     * Endpoint para cadastro de uma nova pessoa.
     *
     * POST /api/v1/pessoas
     *
     * O @Valid aciona as validações definidas no PessoaRequest antes
     * de chegar ao Service — campos inválidos retornam 400 automaticamente.
     *
     * O UriComponentsBuilder constrói o header Location da resposta 201,
     * seguindo a boa prática REST de informar onde o recurso foi criado.
     *
     * @param request    dados da pessoa enviados no corpo da requisição
     * @param uriBuilder utilitário para construção da URI do recurso criado
     * @return 201 Created com os dados cadastrados e o login gerado no corpo
     */
    @PostMapping
    public ResponseEntity<PessoaResponse> cadastrarPessoa(@RequestBody @Valid PessoaRequest request, UriComponentsBuilder uriBuilder) {
        PessoaResponse response = pessoaService.cadastrarPessoa(request);
        var uri = uriBuilder.path("/api/v1/pessoas/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
