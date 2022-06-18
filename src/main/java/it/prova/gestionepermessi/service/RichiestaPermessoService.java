package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {

	public List<RichiestaPermesso> listAllElements();

	public RichiestaPermesso caricaSingoloElemento(Long id);

	public void aggiorna(RichiestaPermesso dipendenteInstance);

	public void aggiornaDipendente(RichiestaPermesso dipendenteInstance);

	public void inserisciNuovo(RichiestaPermesso dipendenteInstance);

	public void rimuovi(Long idDipendente);

	public List<RichiestaPermesso> findByExample(RichiestaPermesso example);

}