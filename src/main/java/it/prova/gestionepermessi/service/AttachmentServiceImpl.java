package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.repository.attachment.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Override
	@Transactional
	public List<Attachment> elencaTutti() {
		return (List<Attachment>) attachmentRepository.findAll();
	}

	@Override
	@Transactional
	public Attachment caricaSingoloElemento(Long id) {
		return attachmentRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Attachment inserisciNuovo(Attachment documentoInstance) {
		return attachmentRepository.save(documentoInstance);
	}
	
	@Override
	@Transactional
	public void rimuovi(Long idAttachment) {
		attachmentRepository.deleteById(idAttachment);
	}

}
