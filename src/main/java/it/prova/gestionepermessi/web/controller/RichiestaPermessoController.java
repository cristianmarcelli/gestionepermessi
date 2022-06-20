package it.prova.gestionepermessi.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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

	// ##################################
	// ########## DIPENDENTE ############
	// ##################################

	// lista richiestepermesso
	@GetMapping("/listAllRichiesteBackoffice")
	public ModelAndView listRichiestePermesso() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> dipendenti = richiestaPermessoService.listAllElements();
		mv.addObject("richiestepermesso_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(dipendenti));
		mv.setViewName("backoffice/richiestapermesso/list");
		return mv;
	}

	// insert richieste da parte del dipendente
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

	// SEARCH richieste singole al dipendente
	@GetMapping("/searchRichiestapermesso")
	public String search(Model model) {

		model.addAttribute("search_richiesta_attr", new RichiestaPermessoDTO());

		return "dipendente/richiestapermesso/search";
	}

	@PostMapping("/listRichieste")
	public String listRichieste(RichiestaPermessoSearchDTO richiestePermessoExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy, ModelMap model) {
		Authentication utenteInPagina = SecurityContextHolder.getContext().getAuthentication();
		Dipendente dipendente = dipendenteService.cercaPerUsername(utenteInPagina.getName());
		Long id = dipendente.getId();
		List<RichiestaPermesso> richiestePermessi = richiestaPermessoService
				.findByExample(richiestePermessoExample, pageNo, pageSize, sortBy).getContent().stream()
				.filter(richiesta -> richiesta.getDipendente().getId() == id).collect(Collectors.toList());
		;
		model.addAttribute("richiestapermesso_dipendente_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermessi));
		return "dipendente/richiestapermesso/list";
	}

	// visualizza richiesta singolo dipendente
	@GetMapping("/showRichiestaPermesso/{idRichiestaPermesso}")
	public String showRichiestaPermesso(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {
		model.addAttribute("show_richiestapermesso_attr",
				richiestaPermessoService.caricaSingoloElemento(idRichiestaPermesso));
		return "dipendente/richiestapermesso/show";
	}

	// rimozione RichiestaPermesso da parte del dipendente se non approvate
	@GetMapping("/deleteRichiestaPermesso/{idRichiestapermesso}")
	public String deleteRichiestaPermesso(@PathVariable(required = true) Long idRichiestapermesso, Model model) {
		model.addAttribute("delete_richiestapermesso_attr",
				richiestaPermessoService.caricaSingoloElemento(idRichiestapermesso));

		return "dipendente/richiestapermesso/delete";
	}

	@PostMapping("/removeRichiestaPermesso")
	public String remove(@RequestParam(required = true) Long idRichiestapermesso, RedirectAttributes redirectAttrs) {

		Messaggio messaggioItem = messaggioService.findByRichiesta(idRichiestapermesso);

//		Attachment attachmentItem = attachmentService.findByRichiesta(idRichiestapermesso);
//
//		if (attachmentItem != null) {
//			attachmentService.rimuovi(attachmentItem.getId());
//		}

		if (messaggioItem != null) {
			messaggioService.rimuovi(messaggioItem.getId());
		}

		richiestaPermessoService.rimuovi(idRichiestapermesso);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "dipendente/richiestapermesso/list";
	}

	// modifica richiesta da parte del dipendente
	@GetMapping("/editRichiestaPermesso/{idRichiestaPermesso}")
	public String edit(@PathVariable(required = true) Long idRichiestaPermesso, Model model) {

		RichiestaPermesso richiestaPermessoModel = richiestaPermessoService.caricaSingoloElemento(idRichiestaPermesso);
		model.addAttribute("edit_richiestapermesso_attr",
				RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaPermessoModel));
		return "dipendente/richiestapermesso/edit";
	}
//
//	@PostMapping("/updateRichiestaPermesso")
//	public String updateRichiestaPermesso(
//			@ModelAttribute("edit_richiestapermesso_attr") RichiestaPermessoDTO richiestaPermessoDTO,
//			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {
//
//		richiestaPermessoService.aggiorna(richiestaPermessoDTO.buildRichiestaPermessoFromModel());
//		
////		richiestaPermessoService.aggiorna(richiestaPermessoDTO.buildRichiestaPermessoFromModel(),
////				richiestaPermessoDTO.getGiornoSingolo(), richiestaPermessoDTO.getAttachment());
//
//		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
//		return "redirect:listRichiestaPermesso";
//	}

	@PostMapping("/updateRichiestaPermesso")
	public String updateRichiestaPermesso(
			@Valid @ModelAttribute("edit_richiestapermesso_attr") RichiestaPermessoDTO richiestaPermessoDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "dipendente/richiestapermesso/edit";
		}
		RichiestaPermesso richiestaPermesso = richiestaPermessoDTO.buildRichiestaPermessoFromModel();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			throw new RuntimeException("Errore!");
		}
		Dipendente dipendente = dipendenteService.cercaPerUsername(auth.getName());
		if (dipendente == null) {
			throw new RuntimeException("Errore!");
		}

		richiestaPermesso.setDipendente(dipendente);

		richiestaPermessoService.aggiorna(richiestaPermesso.getId(), richiestaPermessoDTO.getAttachment());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:listRichiestaPermesso";
	}

	// ##################################
	// ########## BACKOFFICE ############
	// ##################################

	@GetMapping("/showRichiestaPermessoBackoffice/{idRichiestaPermesso}")
	public String showRichiestaPermessoBackoffice(@PathVariable(required = true) Long idRichiestaPermesso,
			Model model) {
		model.addAttribute("show_richiestapermesso_attr",
				richiestaPermessoService.caricaSingoloElemento(idRichiestaPermesso));
		return "backoffice/richiestapermesso/show";
	}

	// search richieste backoffice
	@GetMapping("/searchRichiesteBackoffice")
	public String searchRichiesteBackoffice(Model model) {

		model.addAttribute("search_richiesta_attr", new RichiestaPermessoDTO());

		return "backoffice/richiestapermesso/search";
	}

	// Approva richiesta
	@PostMapping("/approvaRichiesta")
	public String approvaRichiesta(
			@RequestParam(name = "idRichiestaForApprovaRichiesta", required = true) Long idRichiestapermesso, RedirectAttributes redirectAttrs) {

		richiestaPermessoService.approvaRichiesta(idRichiestapermesso);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/richiestapermesso/listAllRichiesteBackoffice";
	}

}
