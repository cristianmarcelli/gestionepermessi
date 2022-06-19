package it.prova.gestionepermessi.repository.dipendente;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long> {
	
	@Query("from Dipendente d join fetch d.utente where d.id = ?1")
	Optional<Dipendente> findByIdConUtente(Long id);
	
	@Query("select d from Dipendente d join fetch d.utente u where u.username = ?1")
	Optional<Dipendente> findByUsername(String username);
	
}