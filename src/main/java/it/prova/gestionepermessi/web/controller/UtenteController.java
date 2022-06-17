package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
@RequestMapping(value = "/utente")
public class UtenteController {
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping("list")
	public ModelAndView listAllDipendenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		// trasformiamo in DTO
		mv.addObject("utenti_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenti));
		mv.setViewName("admin/utente/list");
		return mv;
	}

}
