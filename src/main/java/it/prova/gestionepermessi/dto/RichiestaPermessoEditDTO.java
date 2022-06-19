package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.TipoPermesso;

public class RichiestaPermessoEditDTO {

	private Long id;

	@NotNull(message = "{tipoPermesso.notNull}")
	private TipoPermesso tipoPermesso;

	@NotNull(message = "{dataInizio.notNull}")
	private Date dataInizio;

	private Date dataFine;

	private boolean approvato;

	@NotBlank(message = "{codiceCertificato.notBlanck}")
	private String codiceCertificato;

	private String note;

	private DipendenteDTO dipendenteDTO;

	private MultipartFile attachment;

	public RichiestaPermessoEditDTO(Long id, @NotNull(message = "{tipoPermesso.notNull}") TipoPermesso tipoPermesso,
			@NotNull(message = "{dataInizio.notNull}") Date dataInizio, Date dataFine, boolean approvato,
			@NotBlank(message = "{codiceCertificato.notBlanck}") String codiceCertificato, String note) {
		super();
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
	}

	public RichiestaPermessoEditDTO() {
	}

	public RichiestaPermessoEditDTO(Long id, @NotNull(message = "{tipoPermesso.notNull}") TipoPermesso tipoPermesso,
			@NotNull(message = "{dataInizio.notNull}") Date dataInizio, Date dataFine, boolean approvato,
			@NotBlank(message = "{codiceCertificato.notBlanck}") String codiceCertificato, String note,
			MultipartFile attachment) {
		super();
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
		this.attachment = attachment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPermesso getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(TipoPermesso tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public boolean isApprovato() {
		return approvato;
	}

	public void setApprovato(boolean approvato) {
		this.approvato = approvato;
	}

	public String getCodiceCertificato() {
		return codiceCertificato;
	}

	public void setCodiceCertificato(String codiceCertificato) {
		this.codiceCertificato = codiceCertificato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public DipendenteDTO getDipendenteDTO() {
		return dipendenteDTO;
	}

	public void setDipendenteDTO(DipendenteDTO dipendenteDTO) {
		this.dipendenteDTO = dipendenteDTO;
	}

	public MultipartFile getAttachment() {
		return attachment;
	}

	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}

	public static RichiestaPermessoEditDTO buildRichiestaPermessoFromModel(RichiestaPermesso richiestaPermesso) {
		RichiestaPermessoEditDTO result = new RichiestaPermessoEditDTO(richiestaPermesso.getId(),
				richiestaPermesso.getTipoPermesso(), richiestaPermesso.getDataInizio(), richiestaPermesso.getDataFine(),
				richiestaPermesso.isApprovato(), richiestaPermesso.getCodiceCertificato(), richiestaPermesso.getNote());

		return result;
	}

	public static RichiestaPermessoEditDTO buildRichiestaPermessoConAttachmentFromModel(
			RichiestaPermesso richiestaPermesso) {
		RichiestaPermessoEditDTO result = new RichiestaPermessoEditDTO(richiestaPermesso.getId(),
				richiestaPermesso.getTipoPermesso(), richiestaPermesso.getDataInizio(), richiestaPermesso.getDataFine(),
				richiestaPermesso.isApprovato(), richiestaPermesso.getCodiceCertificato(), richiestaPermesso.getNote(),
				(MultipartFile) richiestaPermesso.getAttachment());

		return result;
	}

	public static List<RichiestaPermessoEditDTO> createRichiestaPermessoDTOListFromModelList(
			List<RichiestaPermesso> modelListInput) {
		return modelListInput.stream().map(ric -> {
			return RichiestaPermessoEditDTO.buildRichiestaPermessoFromModel(ric);
		}).collect(Collectors.toList());
	}

	public RichiestaPermesso buildRichiestaPermessoModel() {
		return new RichiestaPermesso(this.id, this.tipoPermesso, this.dataInizio, this.dataFine, this.approvato,
				this.codiceCertificato, this.note);
	}

	public RichiestaPermesso buildRichiestaPermessoConAttachmentModel() {
		return new RichiestaPermesso(this.id, this.tipoPermesso, this.dataInizio, this.dataFine, this.approvato,
				this.codiceCertificato, this.note, (Attachment) this.attachment);
	}
}