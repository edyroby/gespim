package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.security.Utente

class AreaCompetenza {
	
	String codice
	String descrizione
	
	static hasMany = [utenti:Utente]

    static constraints = {
		codice(blank:false,unique:true)
		descrizione(blank:false,unique:true)
    }
	
	String toString(){
		descrizione
	}
}
