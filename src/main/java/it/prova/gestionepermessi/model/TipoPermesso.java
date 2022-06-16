package it.prova.gestionepermessi.model;

public enum TipoPermesso {
	FERIE("F"), MALATTIA("M");

	private String abbreviazione;

	private TipoPermesso(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

}