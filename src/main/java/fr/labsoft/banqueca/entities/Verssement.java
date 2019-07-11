package fr.labsoft.banqueca.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("V")
public class Verssement extends Operation {

	public Verssement() {
		super();
	}
	
	public Verssement(Date dateOperation, double montant) {
		super(dateOperation, montant);
	} 
	
	@Override
	public String toString() {
		return "Verssement";
	}

}
