package it.prova.gestionepermessi.repository.dipendente;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long> {
	
	

}
