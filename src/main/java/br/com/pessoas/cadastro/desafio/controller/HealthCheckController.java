package br.com.pessoas.cadastro.desafio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável por verificar a disponibilidade da API.
 *
 * Utilizado pelo front-end no carregamento inicial da aplicação
 * para garantir que o back-end está online antes de exibir o formulário.
 *
 * GET /health-check → retorna 200 OK se a API estiver disponível
 */
@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    /**
     * Endpoint de verificação de integridade da API.
     *
     * @return mensagem de confirmação que a API está operacional
     */
    @GetMapping()
    public String testeDeIntegracao() {
        return "Teste de integridade com a API ok!";
    }
}
