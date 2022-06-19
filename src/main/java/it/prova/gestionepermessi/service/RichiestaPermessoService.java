package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {

	public List<RichiestaPermesso> listAllElements();

	public RichiestaPermesso caricaSingoloElemento(Long id);

	public void aggiorna(RichiestaPermesso richiestaPermessoInstance);

	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance, boolean giornoUnico, MultipartFile file);

	public void rimuovi(Long idRichiestaPermesso);

	public List<RichiestaPermesso> findByExample(RichiestaPermesso example);

	public List<RichiestaPermesso> listAllRichiestePermessiPerIdDipendente(Long id);

}