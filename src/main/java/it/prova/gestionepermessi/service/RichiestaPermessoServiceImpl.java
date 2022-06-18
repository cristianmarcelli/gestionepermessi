package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.dipendente.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService {

	@Autowired
	private RichiestaPermessoRepository richiestaPermessoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllElements() {
		return (List<RichiestaPermesso>) richiestaPermessoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingoloElemento(Long id) {
		
		return null;
	}

	@Override
	@Transactional
	public void aggiorna(RichiestaPermesso dipendenteInstance) {
	}

	@Override
	@Transactional
	public void aggiornaDipendente(RichiestaPermesso dipendenteInstance) {
	}

	@Override
	@Transactional
	public void inserisciNuovo(RichiestaPermesso dipendenteInstance) {
	}

	@Override
	@Transactional
	public void rimuovi(Long idDipendente) {
	}

	@Override
	@Transactional
	public List<RichiestaPermesso> findByExample(RichiestaPermesso example) {
		
		return null;
	}

}
