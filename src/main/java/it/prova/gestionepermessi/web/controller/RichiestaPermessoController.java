package it.prova.gestionepermessi.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoSearchDTO;
import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.MessaggioService;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/richiestapermesso")
public class RichiestaPermessoController {

	@Autowired
	private RichiestaPermessoService richiestaPermessoService;

	@Autowired
	private DipendenteService dipendenteService;

	@Autowired
	private MessaggioService messaggioService;

	// lista richiestepermesso
	@GetMapping("listAllRichiesteBackoffice")
	public ModelAndView listAllRichiesteBackoffice() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> richiestePermesso = richiestaPermessoService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("richiestapermesso_dipendente_list_attribute",
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

	// visualizza richiesta singolo dipendente
	@GetMapping("/showRichiestaPermesso/{idRichiestaPermesso}")
	public String showRichiestaPermesso(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		model.addAttribute("show_richiestapermesso_attr",
				richiestaPermessoService.caricaSingoloElemento(idRichiestaPermesso));
		return "dipendente/richiestapermesso/show";
	}

	// deleteRichiestaPermesso
	@GetMapping("/deleteRichiestaPermesso/{idRichiestapermesso}")
	public String deleteRichiestaPermesso(@PathVariable(required = true) Long idRichiestapermesso, Model model) {
		model.addAttribute("delete_richiestapermesso_attr",
				richiestaPermessoService.caricaSingoloElemento(idRichiestapermesso));

		return "dipendente/richiestapermesso/delete";
	}

	@PostMapping("/removeRichiestaPermesso")
	public String remove(@RequestParam(required = true) Long idRichiestapermesso, RedirectAttributes redirectAttrs) {

		Messaggio messaggioItem = messaggioService.findByRichiesta(idRichiestapermesso);

		if (messaggioItem != null) {
			messaggioService.rimuovi(messaggioItem.getId());
		}

		richiestaPermessoService.rimuovi(idRichiestapermesso);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:listRichiestaPermesso";
	}
	
	
	// Approva richiesta
		@PostMapping("/approva")
		public String approva(@RequestParam(name = "idRichiestaForApprova", required = true) Long idRichiestapermesso) {

			
			richiestaPermessoService.approvaRichiesta(idRichiestapermesso);
			
			return "redirect:/dipendente/list";
		}
	
	
	

//	//modifica richiesta
//	@GetMapping("/editRichiestaPermesso/{idRichiestaPermesso}")
//	public String edit(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
//		RichiestaPermesso richiestaPermessoModel = richiestaPermessoService.caricaSingoloElemento(idRichiestaPermesso);
//		model.addAttribute("edit_richiestapermesso_attr", RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaPermessoModel));
//		return "dipendente/richiestapermesso/edit";
//	}
//
//	@PostMapping("/updateRichiestaPermesso")
//	public String updateRichiestaPermesso(@ModelAttribute("edit_richiestapermesso_attr") RichiestaPermessoDTO richiestaPermessoDTO,
//			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
//
//		richiestaPermessoService.aggiornaProva(richiestaPermessoDTO.buildRichiestaPermessoFromModel(), richiestaPermessoDTO.getGiornoSingolo(),
//				richiestaPermessoDTO.getAttachment());
//
//		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
//		return "redirect:/utente";
//	}

	
	
	
	// SEARCH
//	@GetMapping("/searchRichiestapermesso")
//	public String search(Model model) {
//
//		model.addAttribute("search_richiesta_attr", new RichiestaPermessoDTO());
//
//		return "dipendente/richiestapermesso/search";
//	}
//
//	@PostMapping("/listRichiestaPermesso")
//	public String listRichieste(RichiestaPermessoDTO richiestaExample, @RequestParam(defaultValue = "0") Integer pageNo,
//			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
//			ModelMap model) {
//
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//		Long myId = dipendenteService.cercaPerUsername(auth.getName()).getId();
//
//		List<RichiestaPermesso> richieste = richiestaPermessoService.findByExample(richiestaExample, pageNo, pageSize, sortBy)
//				.getContent().stream().filter(richiesta -> richiesta.getDipendente().getId() == myId)
//				.collect(Collectors.toList());
//
//		model.addAttribute("richiestapermesso_dipendente_list_attribute",
//				RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModelList(richieste));
//		return "dipendente/richiestapermesso/list";
//	}
		
		@GetMapping("/searchRichiestapermesso")
		public String search(Model model) {
	
			model.addAttribute("search_richiesta_attr", new RichiestaPermessoDTO());
	
			return "dipendente/richiestapermesso/search";
		}
		

		@PostMapping("/listRichieste")
		public String listRichieste( RichiestaPermessoSearchDTO richiestePermessoExample, @RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
				ModelMap model) {
			Authentication  utenteInPagina= SecurityContextHolder.getContext().getAuthentication();
			Dipendente dipendente= dipendenteService.cercaPerUsername(utenteInPagina.getName());
			Long id= dipendente.getId();
			List<RichiestaPermesso> richiestePermessi = richiestaPermessoService.findByExample(richiestePermessoExample, pageNo, pageSize, sortBy).getContent().stream().filter(richiesta -> richiesta.getDipendente().getId() == id)
					.collect(Collectors.toList());;
			model.addAttribute("richiestapermesso_dipendente_list_attribute", RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermessi));
			return "dipendente/richiestapermesso/list";
		}

}
