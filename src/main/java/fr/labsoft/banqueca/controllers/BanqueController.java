package fr.labsoft.banqueca.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.labsoft.banqueca.entities.Compte;
import fr.labsoft.banqueca.entities.Operation;
import fr.labsoft.banqueca.metier.IBanqueMetier;
import fr.labsoft.banqueca.models.BanqueForm;

@Controller
public class BanqueController {
	
	@Autowired
	private IBanqueMetier metier;
	
	@RequestMapping("/index") // l'URL 
	public String index(Model model) {
		model.addAttribute("banqueForm", new BanqueForm());
		return "banque"; // le nom de la vue
	}
	
	@RequestMapping("/chargerCompte") // l'URL 
	public String charger(@Valid BanqueForm bf, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "banque";
		}
		
		chargerCompte(bf);
		
		model.addAttribute("banqueForm", bf);
		return "banque"; 
	}
	
	@RequestMapping("/saveOperation")  
	public String saveOp(@Valid BanqueForm bf, BindingResult bindingResult) {
		
			try {
				if(bindingResult.hasErrors()) {
					return "banque";
				}
							
				if(bf.getAction() != null) {
					if(bf.getTypeOperation().equals("VER")) {
						metier.verssement(bf.getMontant(), bf.getCode(), 1L);
					} else if(bf.getTypeOperation().equals("RET")) {
						metier.retrait(bf.getMontant(), bf.getCode(), 1L);
					} else if(bf.getTypeOperation().equals("VIR")) {
						metier.virement(bf.getMontant(), bf.getCode(), bf.getCode2(), 1L);
					}
				}				
			} catch (Exception e) {
				bf.setException(e.getMessage());
			}
			chargerCompte(bf);
					
		return "banque"; 
	}
	
	public void chargerCompte(BanqueForm bf) {
		try {
			Compte cp = metier.consulterCompte(bf.getCode());
			bf.setTypeCompte(cp.getClass().getSimpleName());
			bf.setCompte(cp);
			
			int pos = bf.getNbLignes()*bf.getNbPages();
			List<Operation> ops = metier.consulterOperations(bf.getCode(), pos, bf.getNbLignes());
			bf.setOperations(ops);
			long nbOp = metier.getNombreOperation(bf.getCode());
			bf.setNbPages((int)(nbOp/bf.getNbLignes()) + 1);
		} catch (Exception e) {
			bf.setException(e.getMessage());
		}
	}

}
