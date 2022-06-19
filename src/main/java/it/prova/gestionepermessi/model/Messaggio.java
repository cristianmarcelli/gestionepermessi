package it.prova.gestionepermessi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "messaggio")
public class Messaggio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Lob
	@Column(name = "testo")
	private String testo;
	@Column(name = "oggetto")
	private String oggetto;
	@Column(name = "letto")
	private Boolean letto;
	@Column(name = "datainserimento")
	private Date dataInserimento;
	@Column(name = "datalettura")
	private Date dataLettura;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "richiestapermesso_id", referencedColumnName = "id")
	private RichiestaPermesso richiestaPermesso;

	public Messaggio() {
	}

	public Messaggio(Long id, String testo, String oggetto, boolean letto, Date dataInserimento, Date dataLettura,
			RichiestaPermesso richiestaPermesso) {
		super();
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
		this.richiestaPermesso = richiestaPermesso;
	}

	public Messaggio(String testo, String oggetto, Date dataInserimento, Date dataLettura,
			RichiestaPermesso richiestaPermesso) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
		this.richiestaPermesso = richiestaPermesso;
	}

	public Messaggio(String testo, String oggetto, boolean letto, Date dataInserimento, Date dataLettura,
			RichiestaPermesso richiestaPermesso) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
		this.richiestaPermesso = richiestaPermesso;
	}

	public Messaggio(String testo, String oggetto) {
		super();
		this.testo = testo;
		this.oggetto = oggetto;
	}

	public Messaggio(Long id, String testo, String oggetto, Boolean letto, Date dataInserimento, Date dataLettura) {
		super();
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.dataInserimento = dataInserimento;
		this.dataLettura = dataLettura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public boolean isLetto() {
		return letto;
	}

	public void setLetto(boolean letto) {
		this.letto = letto;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataLettura() {
		return dataLettura;
	}

	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	public RichiestaPermesso getRichiestaPermesso() {
		return richiestaPermesso;
	}

	public void setRichiestaPermesso(RichiestaPermesso richiestaPermesso) {
		this.richiestaPermesso = richiestaPermesso;
	}

}
