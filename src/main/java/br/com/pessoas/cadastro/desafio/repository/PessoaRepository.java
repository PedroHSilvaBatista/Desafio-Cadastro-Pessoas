package br.com.pessoas.cadastro.desafio.repository;

import br.com.pessoas.cadastro.desafio.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByDocumentoCpf(String documentoCpf);

    boolean existsByEmail(String email);

    @Query("SELECT p.login FROM Pessoa p")
    List<String> findAllLogins();
}
