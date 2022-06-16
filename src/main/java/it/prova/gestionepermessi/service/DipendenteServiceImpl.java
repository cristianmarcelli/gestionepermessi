package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;

public class DipendenteServiceImpl implements DipendenteService {

	@Autowired
	private DipendenteRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Dipendente> listAllElements() {
		return (List<Dipendente>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Dipendente caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {
		repository.save(dipendenteInstance);
	}

	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		repository.save(dipendenteInstance);
	}

	@Transactional
	public void rimuovi(Long idDipendente) {
		repository.deleteById(idDipendente);
	}

	@Transactional
	public List<Dipendente> findByExample(Dipendente example) {
		//da implementare
		return this.listAllElements();
	}

}
