package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface MessaggioService {

	public void aggiorna(Messaggio messaggioInstance);

	public void inserisciNuovo(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance);

	public void rimuovi(Long idMessaggio);

	public List<Dipendente> findByExample(Messaggio example);

	public Messaggio findByRichiesta(Long idRichiesta);

}