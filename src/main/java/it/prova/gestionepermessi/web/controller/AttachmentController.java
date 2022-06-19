package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionepermessi.dto.AttachmentDTO;
import it.prova.gestionepermessi.service.AttachmentService;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;

	@GetMapping
	public List<AttachmentDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return AttachmentDTO.createAttachmentDTOListFromModelList(attachmentService.elencaTutti());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AttachmentDTO createNew(@RequestBody AttachmentDTO attachmentDTO) {
		return AttachmentDTO.buildAttachmentDTOFromModel(
				attachmentService.inserisciNuovo(attachmentDTO.buildAttachmentModel(attachmentDTO)));
	}

}