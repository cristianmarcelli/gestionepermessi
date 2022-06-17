package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DipendenteRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Dipendente> listAllElements() {
		return (List<Dipendente>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Dipendente caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {
		repository.save(dipendenteInstance);
	}

	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		repository.save(dipendenteInstance);
	}

	@Transactional
	public void rimuovi(Long idDipendente) {
		repository.deleteById(idDipendente);
	}

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

}
