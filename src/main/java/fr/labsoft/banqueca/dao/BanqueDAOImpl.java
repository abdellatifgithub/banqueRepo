package fr.labsoft.banqueca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.labsoft.banqueca.entities.Client;
import fr.labsoft.banqueca.entities.Compte;
import fr.labsoft.banqueca.entities.Employe;
import fr.labsoft.banqueca.entities.Groupe;
import fr.labsoft.banqueca.entities.Operation;

public class BanqueDAOImpl implements IBanqueDAO {

	@PersistenceContext
	private EntityManager entityManager; // gestion de la persistnce -> appel JPA
	
	@Override
	public Client addClient(Client client) {
		entityManager.persist(client); // pour enregistrer client -> appel JPA
		return client;
	}

	@Override
	public Employe addEmploye(Employe employe, Long codeSup) {
		if(codeSup != null) {
			Employe employeSup = entityManager.find(Employe.class, codeSup); // charger un employe
			employe.setEmployeSup(employeSup); // associé employeSup à Employe
		}
		entityManager.persist(employe);
		return employe;
	}

	@Override
	public Groupe addGroupe(Groupe groupe) {
		entityManager.persist(groupe);
		return groupe;
	}

	@Override
	public void addEmployeToGroupe(Long codeEmploye, Long codeGroupe) {
		Employe employe = entityManager.find(Employe.class, codeEmploye);
		Groupe groupe = entityManager.find(Groupe.class, codeGroupe);
		employe.getGroupes().add(groupe);
		groupe.getEmployes().add(employe);
	}

	@Override
	public Compte addCompte(Compte compte, Long codeClient, Long codeEmploye) {
		Client client = entityManager.find(Client.class, codeClient);
		Employe employe = entityManager.find(Employe.class, codeEmploye);
		compte.setClient(client);
		compte.setEmploye(employe);
		entityManager.persist(compte);
		return compte;
	}

	@Override
	public Operation addOperation(Operation operation, String codeCompte, Long codeEmploye) {
		Compte compte = consulterCompte(codeCompte);
		Employe employe = entityManager.find(Employe.class, codeEmploye);
		operation.setCompte(compte);
		operation.setEmploye(employe);
		entityManager.persist(operation);
		return operation;
	}

	@Override
	public Compte consulterCompte(String codeCompte) {
		Compte compte = entityManager.find(Compte.class, codeCompte);
		if(compte == null) throw new RuntimeException("Compte " + codeCompte +  " introuvable !");
		return compte;
	}

	@Override
	public List<Operation> consulterOperations(String codeCompte, int position, int nbOperation) {
		Query query = entityManager.createQuery("select o from Operation o where o.compte.codeCompte=:x order by o.dateOperation desc");
		query.setParameter("x", codeCompte);
		query.setFirstResult(position);
		query.setMaxResults(nbOperation);
		return query.getResultList();
	}

	@Override
	public Client consulterClient(Long codeClient) {
		Client client = entityManager.find(Client.class, codeClient);
		if(client == null) throw new RuntimeException("Client introuvable !");
		return client; 
	}

	@Override
	public List<Client> consulterClient(String motCle) {
		Query query = entityManager.createQuery("select c from Client c where c.nomClient like:x");
		query.setParameter("x", "%"+motCle+"%");
		return query.getResultList();
	}

	@Override
	public List<Compte> getComptesByClient(Long codeClient) {
		Query query = entityManager.createQuery("select c from Compte c where c.client.codeClient=:x");
		query.setParameter("x", codeClient);
		return query.getResultList();
	}

	@Override
	public List<Compte> getComptesByEmploye(Long codeEmploye) {
		Query query = entityManager.createQuery("select c from Compte c where c.employe.codeEmploye=:x");
		query.setParameter("x", codeEmploye);
		return query.getResultList();
	}

	@Override
	public List<Employe> getEmploye() {
		Query query = entityManager.createQuery("select e from Employe e");
		return query.getResultList();
	}

	@Override
	public List<Groupe> getGroupe() {
		Query query = entityManager.createQuery("select g from Groupe g");
		return query.getResultList();
	}

	@Override
	public List<Employe> getEmployeByGroupe(Long codeGroupe) {
		Query query = entityManager.createQuery("select e from Employe e where e.groupe.codeGroupe=:x");
		query.setParameter("x", codeGroupe);
		return query.getResultList();
	}

	@Override
	public long getNombreOperation(String numCompte) {
		Query query = entityManager.createQuery("select count(o) from Operation o where o.compte.codeCompte=:x");
		query.setParameter("x", numCompte);
		return (Long)query.getResultList().get(0);
	}

}
