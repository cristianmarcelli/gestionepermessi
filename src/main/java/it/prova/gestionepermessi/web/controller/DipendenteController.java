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
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.service.DipendenteService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;

	// lista dipendenti
	@GetMapping("list")
	public ModelAndView listAllDipendenti() {
		ModelAndView mv = new ModelAndView();
		List<Dipendente> dipendenti = dipendenteService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("dipendenti_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		mv.setViewName("admin/dipendente/list");
		return mv;
	}

	// visualizza dipendenti nella lista
	@GetMapping("/show/{idDipendente}")
	public String showDipendente(@PathVariable(required = true) Long idDipendente, Model model) {
		model.addAttribute("show_dipendente_attr", dipendenteService.caricaSingoloElemento(idDipendente));
		return "admin/dipendente/show";	
	}

	// Ricerca dipendenti in admin
	@GetMapping("/search")
	public String searchDipendente(Model model) {
		model.addAttribute("dipendenti_list_attribute",
				DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllElements()));
		return "admin/dipendente/search";
	}

	@PostMapping("/listFindByExample")
	public String listDipendenti(DipendenteDTO dipendenteExample, ModelMap model) {
		List<Dipendente> dipendenti = dipendenteService.findByExample(dipendenteExample.buildDipendenteModel());
		model.addAttribute("dipendenti_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		return "admin/dipendente/list";
	}

}
