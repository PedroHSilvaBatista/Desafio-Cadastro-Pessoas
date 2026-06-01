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

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponse> cadastrarPessoa(@RequestBody @Valid PessoaRequest request, UriComponentsBuilder uriBuilder) {
        PessoaResponse response = pessoaService.cadastrarPessoa(request);
        var uri = uriBuilder.path("/api/v1/pessoas/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
