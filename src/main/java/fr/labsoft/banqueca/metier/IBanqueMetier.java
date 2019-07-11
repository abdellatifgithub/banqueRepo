package fr.labsoft.banqueca.metier;

import java.util.List;

import fr.labsoft.banqueca.entities.Client;
import fr.labsoft.banqueca.entities.Compte;
import fr.labsoft.banqueca.entities.Employe;
import fr.labsoft.banqueca.entities.Groupe;
import fr.labsoft.banqueca.entities.Operation;

public interface IBanqueMetier {
	
	public Client addClient(Client client);
	public Employe addEmploye(Employe employe, Long codeSup);
	public Groupe addGroupe(Groupe groupe);
	public void addEmployeToGroupe(Long codeEmploye, Long codeGroupe);
	public Compte addCompte(Compte compte, Long codeClient, Long codeEmploye);	
	public void verssement(double montant, String codeCompte, Long codeEmploye);
	public void retrait(double montant, String codeCompte, long codeEmploye);
	public void virement(double montant, String codeCompte1, String codeCompte2, long codeEmploye);	
	public Compte consulterCompte(String codeCompte);
	public List<Operation> consulterOperations(String codeCompte, int position, int nbOperation);
	public Client consulterClient(Long codeClient);
	public List<Client> consulterClient(String motCle);
	public List<Compte> getComptesByClient(Long codeClient);
	public List<Compte> getComptesByEmploye(Long codeEmploye);
	public List<Employe> getEmploye();
	public List<Groupe> getGroupe();
	public List<Employe> getEmployeByGroupe(Long codeGroupe);
	public long getNombreOperation(String numCompte);

}
