package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;
import it.prova.gestionepermessi.repository.utente.UtenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DipendenteRepository dipendenteRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private RuoloService ruoloServiceInstance;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public List<Dipendente> listAllElements() {
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Dipendente caricaSingoloElemento(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

	@Override
	@Transactional
	public void aggiornaDipendente(Dipendente dipendenteInstance) {
		Dipendente dipendenteDaModificare = dipendenteRepository.findByIdConUtente(dipendenteInstance.getId())
				.orElse(null);
		if (dipendenteDaModificare == null || dipendenteDaModificare.getUtente() == null) {
			throw new RuntimeException("Elemento non trovato.");
		}

		dipendenteInstance.setUtente(dipendenteDaModificare.getUtente());
		dipendenteInstance.getUtente().setUsername(dipendenteInstance.getNome().toLowerCase().charAt(0) + "."
				+ dipendenteInstance.getCognome().toLowerCase());
		dipendenteInstance.setEmail(dipendenteInstance.getUtente().getUsername() + "@prova.it");

		utenteRepository.save(dipendenteInstance.getUtente());
		dipendenteRepository.save(dipendenteInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idDipendente) {
		dipendenteRepository.deleteById(idDipendente);
	}

	@Override
	@Transactional
	public List<Dipendente> findByExample(Dipendente example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select d from Dipendente d where d.id = d.id ");

		if (StringUtils.isNotEmpty(example.getNome())) {
			whereClauses.add(" d.nome  like :nome ");
			paramaterMap.put("nome", "%" + example.getNome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getCognome())) {
			whereClauses.add(" d.cognome like :cognome ");
			paramaterMap.put("cognome", "%" + example.getCognome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getEmail())) {
			whereClauses.add(" d.email like :email ");
			paramaterMap.put("email", "%" + example.getEmail() + "%");
		}
		if (example.getDataNascita() != null) {
			whereClauses.add("d.dataNascita >= :dataNascita ");
			paramaterMap.put("dataNascita", example.getDataNascita());
		}
		if (example.getDataAssunzione() != null) {
			whereClauses.add("d.dataAssunzione >= :dataAssunzione ");
			paramaterMap.put("dataAssunzione", example.getDataAssunzione());
		}
		if (example.getDataDimissioni() != null) {
			whereClauses.add("d.dataDimissioni >= :dataDimissioni ");
			paramaterMap.put("dataDimissioni", example.getDataDimissioni());
		}
		if (example.getSesso() != null) {
			whereClauses.add(" d.sesso =:sesso ");
			paramaterMap.put("sesso", example.getSesso());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Dipendente> typedQuery = entityManager.createQuery(queryBuilder.toString(), Dipendente.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void inserisciUtenteEDipendente(Dipendente dipendenteInstance) {

		Utente utenteInstance = new Utente();

		// Comincio a fare logica di business sull'utente, così da inserire i dati
		// appropriati una volta inserito il dipendente nel DB
		// stato, username, password, ruolo - email

		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode("Password@01"));
		utenteInstance.setDateCreated(new Date());
		utenteInstance.setUsername(dipendenteInstance.getNome().toLowerCase().charAt(0) + "."
				+ dipendenteInstance.getCognome().toLowerCase());
		utenteInstance.getRuoli()
				.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));

		dipendenteInstance.setDataDimissioni(null);
		dipendenteInstance.setEmail(utenteInstance.getUsername() + "@prova.it");

		// Setto l'utente al dipendente e viceversa
		utenteInstance.setDipendente(dipendenteInstance);
		dipendenteInstance.setUtente(utenteInstance);

		dipendenteRepository.save(dipendenteInstance);
		utenteRepository.save(utenteInstance);
	}

	@Override
	@Transactional
	public Dipendente caricaSingoloElementoConUtente(Long id) {
		return dipendenteRepository.findByIdConUtente(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Dipendente cercaPerUsername(String username) {
		return dipendenteRepository.findByUsername(username).orElse(null);
	}

}
