package it.solvingteam.gespim.documentazione

import it.solvingteam.gespim.tipologiche.TipologiaLegale;

class Atto {
	
	String descrizione
	String nomeTemplate
	TipoAtto tipoAtto
	TipologiaLegale tipologiaLegale
	
	//Map mappaCampiInput = [:]

    static constraints = {
		descrizione(nullable:true)
		nomeTemplate(nullable:true)
		tipoAtto(nullable:true)
		//mappaCampiInput(nullable:true) 
		tipologiaLegale(nullable:true)
	}
}
