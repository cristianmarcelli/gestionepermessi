package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.RuoloDTO;
import it.prova.gestionepermessi.dto.UtenteDTO;
import it.prova.gestionepermessi.dto.UtenteSearchDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@Controller
@RequestMapping(value = "/utente")
public class UtenteController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private RuoloService ruoloService;
	
	//Lista di utenti
	@GetMapping("list")
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		// trasformiamo in DTO
		mv.addObject("utenti_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenti));
		mv.setViewName("admin/utente/list");
		return mv;
	}
	
	//Visualizza dettaglio Utente
	@GetMapping("/show/{idUtente}")
	public String showUtente(@PathVariable(required = true) Long idUtente, Model model) {
		List<RuoloDTO> ruoli = RuoloDTO
				.createRuoloDTOListFromModelSet(utenteService.caricaSingoloUtenteConRuoli(idUtente).getRuoli());
		UtenteDTO utenteDTO = UtenteDTO.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(idUtente));
		utenteDTO.setRuoli(ruoli);
		model.addAttribute("show_utente_attr", utenteDTO);
		return "admin/utente/show";
	}
	
	//Ricerca utenti
	@GetMapping("/search")
	public String searchUtente(Model model) {
		model.addAttribute("ruoli_totali_attr", RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()));
		return "admin/utente/search";
	}

	@PostMapping("/listFindByExample")
	public String listUtenti(UtenteSearchDTO utenteExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		List<Utente> utenti = utenteService
				.findByExample(utenteExample, pageNo, pageSize, sortBy).getContent();

		model.addAttribute("utenti_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenti));
		return "admin/utente/list";
	}

}
