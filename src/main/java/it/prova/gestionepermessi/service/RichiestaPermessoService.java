package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.dto.RichiestaPermessoSearchDTO;
import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {

	public List<RichiestaPermesso> listAllElements();

	public RichiestaPermesso caricaSingoloElemento(Long id);

	public void aggiorna(Long idRichiestapermesso, MultipartFile file);

	public void inserisciNuovo(RichiestaPermesso richiestaPermessoInstance, boolean giornoUnico, MultipartFile file);

	public void rimuovi(Long idRichiestaPermesso);

	public Page<RichiestaPermesso> findByExample(RichiestaPermessoSearchDTO example, Integer pageNo, Integer pageSize,
			String sortBy);

	public List<RichiestaPermesso> listAllRichiestePermessiPerIdDipendente(Long id);

	public void approvaRichiesta(Long idRichiestaPermesso);

	RichiestaPermesso caricaSingolaRichiestaConAttachment(Long id);
	
}