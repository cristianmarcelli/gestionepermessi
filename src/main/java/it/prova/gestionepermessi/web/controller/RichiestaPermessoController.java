package it.prova.gestionepermessi.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/richiestapermesso")
public class RichiestaPermessoController {

	@Autowired
	private RichiestaPermessoService richiestaPermessoService;

	@Autowired
	private DipendenteService dipendenteService;

	// lista richiestepermesso
	@GetMapping("listAllRichiesteBackoffice")
	public ModelAndView listAllRichiesteBackoffice() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> richiestePermesso = richiestaPermessoService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("richiestepermesso_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermesso));
		mv.setViewName("backoffice/richiestapermesso/list");
		return mv;
	}

	// ###############################
	// insert richieste
	@GetMapping("/insertRichiestapermesso")
	public String insertRichiesta(Model model) {
		model.addAttribute("insert_richiestapermesso_attr", new RichiestaPermessoDTO());
		return "dipendente/richiestapermesso/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_richiestapermesso_attr") RichiestaPermessoDTO richiestaDTO,
			BindingResult result, RedirectAttributes redirectAttrs) {

		RichiestaPermesso richiestaDaInserire = richiestaDTO.buildRichiestaPermessoFromModel();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			throw new RuntimeException("Error");
		}
		
		Dipendente dipendenteInsessione = dipendenteService.cercaPerUsername(auth.getName());
		if (dipendenteInsessione == null) {
			throw new RuntimeException("Error");
		}

		richiestaDaInserire.setDipendente(dipendenteInsessione);
		richiestaPermessoService.inserisciNuovo(richiestaDaInserire, richiestaDTO.getGiornoSingolo(),
				richiestaDTO.getAttachment());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:listRichiestaPermesso";
	}

	// lista richieste singolo dipendente
	@GetMapping("/listRichiestaPermesso")
	public ModelAndView listRichiestaPermessoDipendente() {
		ModelAndView mv = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null) {
			throw new RuntimeException("Error");
		}
		
		Dipendente dipendenteSpecifico = dipendenteService.cercaPerUsername(auth.getName());

		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService
				.listAllRichiestePermessiPerIdDipendente(dipendenteSpecifico.getId());

		mv.addObject("richiestapermesso_dipendente_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermessi));
		mv.setViewName("dipendente/richiestapermesso/list");
		return mv;
	}

}
