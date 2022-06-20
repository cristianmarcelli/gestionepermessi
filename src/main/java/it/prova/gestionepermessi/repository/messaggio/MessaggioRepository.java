package it.prova.gestionepermessi.repository.messaggio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends CrudRepository<Messaggio, Long> {

	Messaggio findByRichiestaPermesso_Id(Long idRichiesta);
	
	@Query("select m From Messaggio m join fetch m.richiestaPermesso where m.id = ?1")
	Messaggio findByIdEager(Long idMessaggio);

}