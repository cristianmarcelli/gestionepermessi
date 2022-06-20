package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.messaggio.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {

	@Autowired
	private MessaggioRepository messaggioRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Messaggio> listAllElements() {
		return (List<Messaggio>) messaggioRepository.findAll();
	}

	@Override
	@Transactional
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
		messaggioInstance.setLetto(false);
		messaggioInstance.setRichiestaPermesso(richiestaInstance);

		messaggioRepository.save(messaggioInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idMessaggio) {
		messaggioRepository.deleteById(idMessaggio);
	}

	@Override
	@Transactional(readOnly = true)
	public Messaggio findByRichiesta(Long idRichiesta) {
		return messaggioRepository.findByRichiestaPermesso_Id(idRichiesta);
	}

	@Override
	@Transactional
	public Messaggio caricaSingoloMessaggio(Long idMessaggio) {
		return messaggioRepository.findById(idMessaggio).orElse(null);
	}

	@Override
	@Transactional
	public Messaggio caricaSingoloMessaggioEager(Long idMessaggio) {
		return messaggioRepository.findByIdEager(idMessaggio);
	}

	@Override
	@Transactional
	public List<Messaggio> findByExample(Messaggio example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select m from Messaggio m where m.id = m.id ");

		if (StringUtils.isNotEmpty(example.getTesto())) {
			whereClauses.add(" m.testo  like :testo ");
			paramaterMap.put("testo", "%" + example.getTesto() + "%");
		}
		if (StringUtils.isNotEmpty(example.getOggetto())) {
			whereClauses.add(" m.oggetto like :oggetto ");
			paramaterMap.put("oggetto", "%" + example.getOggetto() + "%");
		}
		if (example.getDataInserimento() != null) {
			whereClauses.add("m.dataInserimento >= :dataInserimento ");
			paramaterMap.put("dataInserimento", example.getDataInserimento());
		}
		if (example.getDataLettura() != null) {
			whereClauses.add("m.dataLettura >= :dataLettura ");
			paramaterMap.put("dataLettura", example.getDataLettura());
		}

		if (example.isLetto() == true) {
			whereClauses.add(" m.letto = true ");
		} else if (example.isLetto() == false) {
			whereClauses.add(" m.letto = false ");
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Messaggio> typedQuery = entityManager.createQuery(queryBuilder.toString(), Messaggio.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

	@Override
	public void setLetturaTrue(Long idMessaggio) {

		Messaggio messaggioInstance = caricaSingoloMessaggio(idMessaggio);

		messaggioInstance.setDataLettura(new Date());
		messaggioInstance.setLetto(true);

	}

}
