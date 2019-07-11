package fr.labsoft.banqueca.metier;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import fr.labsoft.banqueca.dao.IBanqueDAO;
import fr.labsoft.banqueca.entities.Client;
import fr.labsoft.banqueca.entities.Compte;
import fr.labsoft.banqueca.entities.Employe;
import fr.labsoft.banqueca.entities.Groupe;
import fr.labsoft.banqueca.entities.Operation;
import fr.labsoft.banqueca.entities.Retrait;
import fr.labsoft.banqueca.entities.Verssement;

@Transactional // spring transaction "gestion des transaction par spring"
public class BanqueMetierImpl implements IBanqueMetier {
	
	private IBanqueDAO dao;

	public void setDao(IBanqueDAO dao) {
		this.dao = dao;
	}

	@Override
	public Client addClient(Client client) {
		return dao.addClient(client);
	}

	@Override
	public Employe addEmploye(Employe employe, Long codeSup) {
		return dao.addEmploye(employe, codeSup);
	}

	@Override
	public Groupe addGroupe(Groupe groupe) {
		return dao.addGroupe(groupe);
	}

	@Override
	public void addEmployeToGroupe(Long codeEmploye, Long codeGroupe) {
		dao.addEmployeToGroupe(codeEmploye, codeGroupe);
		
	}

	@Override
	public Compte addCompte(Compte compte, Long codeClient, Long codeEmploye) {
		return dao.addCompte(compte, codeClient, codeEmploye);
	}

	@Override
	public void verssement(double montant, String codeCompte, Long codeEmploye) {
		dao.addOperation(new Verssement(new Date(), montant), codeCompte, codeEmploye);
		Compte compte = dao.consulterCompte(codeCompte);
		compte.setSolde(compte.getSolde() + montant);
	}

	@Override
	public void retrait(double montant, String codeCompte, long codeEmploye) {
		dao.addOperation(new Retrait(new Date(), montant), codeCompte, codeEmploye);
		Compte compte = dao.consulterCompte(codeCompte);
		compte.setSolde(compte.getSolde() - montant);
	}

	@Override
	public void virement(double montant, String codeCompte1, String codeCompte2, long codeEmploye) {
		retrait(montant, codeCompte1, codeEmploye);
		verssement(montant, codeCompte2, codeEmploye);
	}

	@Override
	public Compte consulterCompte(String codeCompte) {
		return dao.consulterCompte(codeCompte);
	}

	@Override
	public List<Operation> consulterOperations(String codeCompte, int position, int nbOperation) {
		return dao.consulterOperations(codeCompte, position, nbOperation);
	}

	@Override
	public Client consulterClient(Long codeClient) {
		return dao.consulterClient(codeClient);
	}

	@Override
	public List<Client> consulterClient(String motCle) {
		return dao.consulterClient(motCle);
	}

	@Override
	public List<Compte> getComptesByClient(Long codeClient) {
		return dao.getComptesByClient(codeClient);
	}

	@Override
	public List<Compte> getComptesByEmploye(Long codeEmploye) {
		return dao.getComptesByEmploye(codeEmploye);
	}

	@Override
	public List<Employe> getEmploye() {
		return dao.getEmploye();
	}

	@Override
	public List<Groupe> getGroupe() {
		return dao.getGroupe();
	}

	@Override
	public List<Employe> getEmployeByGroupe(Long codeGroupe) {
		return dao.getEmployeByGroupe(codeGroupe);
	}

	@Override
	public long getNombreOperation(String numCompte) {
		return dao.getNombreOperation(numCompte);
	}

}
