package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.MessaggioDTO;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.service.MessaggioService;

@Controller
@RequestMapping(value = "/messaggio")
public class MessaggioController {

	@Autowired
	private MessaggioService messaggioService;

	//listAll messaggi
	@GetMapping("list")
	public ModelAndView listAllMessaggi() {
		ModelAndView mv = new ModelAndView();
		List<Messaggio> messaggi = messaggioService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("messaggi_list_attribute", MessaggioDTO.buildMessaggioDTOFromModelList(messaggi));
		mv.setViewName("backoffice/messaggio/list");
		return mv;
	}
	
	//Visualizza singolo messaggio
	@GetMapping("/show/{idMessaggio}")
	public String showUtente(@PathVariable(required = true) Long idMessaggio, Model model) {
//		MessaggioDTO messaggioDTO = MessaggioDTO.buildMessaggioDTOFromModel(messaggioService.caricaSingoloMessaggio(idMessaggio));
		
		
		
		model.addAttribute("show_messaggio_attr", messaggioService.caricaSingoloMessaggioEager(idMessaggio));
		return "backoffice/messaggio/show";
	}

}
