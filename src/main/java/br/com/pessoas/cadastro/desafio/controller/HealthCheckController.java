package br.com.pessoas.cadastro.desafio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping()
    public String testeDeIntegracao() {
        return "Teste de integridade com a API ok!";
    }
}
