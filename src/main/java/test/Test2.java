package test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.labsoft.banqueca.entities.Client;
import fr.labsoft.banqueca.entities.Compte;
import fr.labsoft.banqueca.entities.Operation;
import fr.labsoft.banqueca.metier.IBanqueMetier;

public class Test2 {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		IBanqueMetier metier = (IBanqueMetier) context.getBean("metier");
		
		Compte cp = metier.consulterCompte("CC1");
		
		System.out.println("***********************");
		System.out.println("Solde: " + cp.getSolde());
		System.out.println("Date: " + cp.getDateCreation());
		System.out.println("Client: " + cp.getClient().getNomClient());
		System.out.println("Employé: " + cp.getEmploye().getNomEmploye());
		
		
		
		List<Operation> ops = metier.consulterOperations("CC1", 0, 5);
		for (Operation op : ops) {
			System.out.println("***********************");
			System.out.println(op.getNumeroOperation());
			System.out.println(op.getDateOperation());
			System.out.println(op.getMontant());
			System.out.println(op.getClass().getSimpleName());
		}

	}

}
