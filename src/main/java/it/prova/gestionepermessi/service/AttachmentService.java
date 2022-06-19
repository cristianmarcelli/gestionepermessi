package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Attachment;

public interface AttachmentService {

	List<Attachment> elencaTutti();

	Attachment caricaSingoloElemento(Long id);

	Attachment inserisciNuovo(Attachment attachmentInstance);

	public void rimuovi(Long idAttachment);

}
