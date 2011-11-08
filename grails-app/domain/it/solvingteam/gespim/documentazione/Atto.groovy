package it.solvingteam.gespim.documentazione

class Atto {
	
	String descrizione
	String nomeTemplate
	TipoAtto tipoAtto
	
	//Map mappaCampiInput = [:]

    static constraints = {
		descrizione(nullable:true)
		nomeTemplate(nullable:true)
		tipoAtto(nullable:true)
		//mappaCampiInput(nullable:true)    
	}
}
