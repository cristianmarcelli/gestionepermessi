package it.prova.gestionepermessi.repository.utente;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

	@EntityGraph(attributePaths = { "ruoli" })
	Optional<Utente> findByUsername(String username);

	@Query("from Utente u left join fetch u.ruoli where u.id = ?1")
	Optional<Utente> findByIdConRuoli(Long id);

	Utente findByUsernameAndPassword(String username, String password);

	// caricamento eager, ovviamente si può fare anche con jpql
	@EntityGraph(attributePaths = { "ruoli" })
	Utente findByUsernameAndPasswordAndStato(String username, String password, StatoUtente stato);

	Page<Utente> findAll(Specification<Utente> specificationCriteria, Pageable paging);

	@Modifying
	@Query("update Utente u set u.password = ?2 where u.id = ?1")
	void resetPasswordRepository(Long idUtente, String password);

}