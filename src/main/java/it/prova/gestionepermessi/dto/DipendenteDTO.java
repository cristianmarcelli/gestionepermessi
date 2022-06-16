package it.prova.gestionepermessi.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.validation.ValidationNoPassword;
import it.prova.gestionepermessi.validation.ValidationWithPassword;

public class DipendenteDTO {
	
	private Long id;
	
	@NotBlank
	@Size(min = 2, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String nome;
	@NotBlank
	@Size(min = 2, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String cognome;
	@NotBlank
	@Size(min = 16, max = 16, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String codFis;
	
	private String email;
	
	private Date dataNascita;
	
	private Date dataAssunzione;
	
	private Date dataDimissioni;
	
	private Sesso sesso;
	
	private UtenteDTO utenteDTO;
	
	

}
