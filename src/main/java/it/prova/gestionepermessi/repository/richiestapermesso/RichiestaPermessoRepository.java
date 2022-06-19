package it.prova.gestionepermessi.repository.richiestapermesso;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoRepository extends CrudRepository<RichiestaPermesso, Long> {

	List<RichiestaPermesso> findAllByDipendente_Id(Long id);

	Page<RichiestaPermesso> findAll(Specification<RichiestaPermesso> specificationCriteria, Pageable paging);

}
