package br.com.pessoas.cadastro.desafio.repository;

import br.com.pessoas.cadastro.desafio.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByDocumentoCpf(String documentoCpf);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
