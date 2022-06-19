package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {
	
	public List<Messaggio> listAllElements();

	public void inserisciNuovo(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance);

	public void rimuovi(Long idMessaggio);

	public Messaggio findByRichiesta(Long idRichiesta);

}