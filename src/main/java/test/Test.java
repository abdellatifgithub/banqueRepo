package test;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.labsoft.banqueca.entities.Client;
import fr.labsoft.banqueca.entities.CompteCourant;
import fr.labsoft.banqueca.entities.CompteEpargne;
import fr.labsoft.banqueca.entities.Employe;
import fr.labsoft.banqueca.entities.Groupe;
import fr.labsoft.banqueca.metier.IBanqueMetier;

public class Test {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		IBanqueMetier metier = (IBanqueMetier) context.getBean("metier");
		
		metier.addClient(new Client("C1", "AD1"));
		metier.addClient(new Client("C2", "AD2"));
		metier.addEmploye(new Employe("E1"), null);
		metier.addEmploye(new Employe("E1"), 1L);
		metier.addEmploye(new Employe("E1"), 1L);
		metier.addGroupe(new Groupe("G1"));
		metier.addGroupe(new Groupe("G2"));
		metier.addEmployeToGroupe(1L, 1L);
		metier.addEmployeToGroupe(2L, 2L);
		metier.addEmployeToGroupe(3L, 2L);
		metier.addCompte(new CompteCourant("CC1", new Date(), 9000, 800), 1L, 2L);
		metier.addCompte(new CompteEpargne("CE1", new Date(), 40000, 5.5), 2L, 2L);
		metier.verssement(5000, "CC1", 2L);
		metier.retrait(6000, "CC1", 2L);
		metier.virement(4000, "CC1", "CE1", 1L);

	}

}
