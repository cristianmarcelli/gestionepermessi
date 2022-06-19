package it.prova.gestionepermessi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.messaggio.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	public List<Messaggio> listAllElements() {

		return null;
	}

	@Override
	public Dipendente caricaSingoloElemento(Long id) {

		return null;
	}

	@Override
	public void aggiorna(Messaggio messaggioInstance) {
	}

	public void inserisciNuovo(Messaggio messaggioInstance, RichiestaPermesso richiestaInstance) {
		String note = richiestaInstance.getNote().isBlank() ? ""
				: " , le note del dipendente " + richiestaInstance.getNote();
		String codiceCertificato = richiestaInstance.getCodiceCertificato().isBlank() ? ""
				: " , il Codice del Certificato: " + richiestaInstance.getCodiceCertificato();
		String attachment = richiestaInstance.getAttachment() == null ? "" : " , il file allegato";
		String parteFinaleMessaggio = ".";

		if (!note.isBlank() || !codiceCertificato.isBlank() || !attachment.isBlank()) {
			parteFinaleMessaggio += " In allegato :";
			parteFinaleMessaggio += note.isBlank() ? "" : " " + note;
			parteFinaleMessaggio += codiceCertificato.isBlank() ? "" : " " + codiceCertificato;
			parteFinaleMessaggio += attachment.isBlank() ? "" : " " + attachment;
			parteFinaleMessaggio += ".";
		}

		messaggioInstance.setOggetto("Richiesta Permesso di " + richiestaInstance.getDipendente().getNome() + " "
				+ richiestaInstance.getDipendente().getCognome());

		messaggioInstance.setTesto("Il dipendente " + richiestaInstance.getDipendente().getNome() + " "
				+ richiestaInstance.getDipendente().getCognome() + " ha richiesto un permesso per "
				+ richiestaInstance.getTipoPermesso() + " a partire dal giorno " + richiestaInstance.getDataInizio()
				+ " al giorno " + richiestaInstance.getDataFine() + parteFinaleMessaggio);

		messaggioInstance.setDataInserimento(new Date());
		messaggioInstance.setLetto(true);
		messaggioInstance.setRichiestaPermesso(richiestaInstance);

		messaggioRepository.save(messaggioInstance);
	}

	@Override
	public void rimuovi(Long idMessaggio) {
	}

	@Override
	public List<Dipendente> findByExample(Messaggio example) {

		return null;
	}

}
