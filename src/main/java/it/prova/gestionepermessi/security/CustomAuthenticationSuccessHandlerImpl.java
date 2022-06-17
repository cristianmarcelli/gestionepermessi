package it.prova.gestionepermessi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.utente.UtenteRepository;

@Component
public class CustomAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//voglio mettere in sessione uno userInfo perché spring security mette solo un principal da cui attingere username
		Utente utenteFromDb = utenteRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Username " + authentication.getName() + " not found"));
		UtenteDTO utenteParziale = new UtenteDTO();
		utenteParziale.setUsername(utenteFromDb.getUsername());
		utenteParziale.setId(utenteFromDb.getId());
		
		//Voglio assegnare il corrispettivo dipendente all'utente parziale così da poter mostrare nome e cognome nella index
		Dipendente dipendenteFromDb = utenteFromDb.getDipendente();
		DipendenteDTO dipendenteParziale = new DipendenteDTO();
		dipendenteParziale.setNome(dipendenteFromDb.getNome());
		dipendenteParziale.setCognome(dipendenteFromDb.getCognome());
		
		utenteParziale.setDipendenteDTO(dipendenteParziale);
		
		
		request.getSession().setAttribute("userInfo", utenteParziale);
		response.sendRedirect("home");

	}

}
