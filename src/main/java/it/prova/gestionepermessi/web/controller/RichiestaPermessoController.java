package it.prova.gestionepermessi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.gestionepermessi.dto.DipendenteDTO;
import it.prova.gestionepermessi.dto.RichiestaPermessoDTO;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.service.RichiestaPermessoService;

@Controller
@RequestMapping(value = "/richiestapermesso")
public class RichiestaPermessoController {

	@Autowired
	private RichiestaPermessoService richiestaPermessoService;

	// lista richiestepermesso
	@GetMapping("listAllRichiesteBackoffice")
	public ModelAndView listAllRichiesteBackoffice() {
		ModelAndView mv = new ModelAndView();
		List<RichiestaPermesso> richiestePermesso = richiestaPermessoService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("richiestepermesso_list_attribute",
				RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(richiestePermesso));
		mv.setViewName("dipendente/richiestapermesso/list");
		return mv;
	}

}
