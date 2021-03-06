package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteService {

	public List<Dipendente> listAllElements();

	public Dipendente caricaSingoloElemento(Long id);
	
	public Dipendente caricaSingoloElementoConUtente(Long id);

	public void aggiorna(Dipendente dipendenteInstance);
	
	public void aggiornaDipendente(Dipendente dipendenteInstance);
	
	public void inserisciNuovo(Dipendente dipendenteInstance);

	public void rimuovi(Long idDipendente);

	public List<Dipendente> findByExample(Dipendente example);
	
	//
	public void inserisciUtenteEDipendente(Dipendente dipendenteInstance);
	
	Dipendente cercaPerUsername(String username);
	
	public List<Dipendente> cercaByCognomeENomeILike(String term);
	
}