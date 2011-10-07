package it.solvingteam.gespim.workflow

import it.solvingteam.gespim.pratica.Pratica;

class IterPratica {
	
	Stato stato
	Date data
	
	
	static belongsTo = [pratica:Pratica]
	
    static constraints = {
		
    }
}
