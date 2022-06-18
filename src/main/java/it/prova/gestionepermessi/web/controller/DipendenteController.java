package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.service.DipendenteService;

@Controller
@RequestMapping(value = "/dipendente")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;

	// ####################################
	// ########### ADMIN ##################
	// ####################################

	// lista dipendenti
	@GetMapping("list")
	public ModelAndView listAllDipendentiAdmin() {
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

	// ####################################
	// ########### BACKOFFICE #############
	// ####################################

	// lista dipendenti
	@GetMapping("listDipendentiBackoffice")
	public ModelAndView listAllDipendentiBackoffice() {
		ModelAndView mv = new ModelAndView();
		List<Dipendente> dipendenti = dipendenteService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("dipendenti_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		mv.setViewName("backoffice/dipendente/list");
		return mv;
	}

	// visualizza dipendenti nella lista
	@GetMapping("/showDipendenteBackoffice/{idDipendente}")
	public String showDipendenteBackoffice(@PathVariable(required = true) Long idDipendente, Model model) {
		model.addAttribute("show_dipendente_attr", dipendenteService.caricaSingoloElemento(idDipendente));
		return "backoffice/dipendente/show";
	}

	// Ricerca dipendenti in admin
	@GetMapping("/searchBackoffice")
	public String searchDipendenteBackoffice(Model model) {
		model.addAttribute("dipendenti_list_attribute",
				DipendenteDTO.createDipendenteDTOListFromModelList(dipendenteService.listAllElements()));
		return "admin/dipendente/search";
	}

	@PostMapping("/listFindByExampleBackoffice")
	public String searchDipendenteBackoffice(DipendenteDTO dipendenteExample, ModelMap model) {
		List<Dipendente> dipendenti = dipendenteService.findByExample(dipendenteExample.buildDipendenteModel());
		model.addAttribute("dipendenti_list_attribute", DipendenteDTO.createDipendenteDTOListFromModelList(dipendenti));
		return "admin/dipendente/list";
	}

	// Inserisci nuovo Dipendente/Utente
	@GetMapping("/insert")
	public String createDipendente(Model model) {
		model.addAttribute("insert_dipendente_attr", new DipendenteDTO());
		return "backoffice/dipendente/insert";
	}
	
	@PostMapping("/save")
	public String saveDipendente(@Valid @ModelAttribute("insert_dipendente_attr") DipendenteDTO dipendenteDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {
		
		
		
		if (result.hasErrors()) {
			return "backoffice/dipendente/insert";
		}
		
		dipendenteService.inserisciUtenteEDipendente(dipendenteDTO.buildDipendenteModel());
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/dipendente/list";
	}

}
