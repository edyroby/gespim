package it.solvingteam.gespim.workflow

import it.solvingteam.gespim.assegnazione.AreaCompetenza;
import it.solvingteam.gespim.documentazione.Atto

class Stato {
	
	String descrizione
	AreaCompetenza area
	
	static hasMany = [atti:Atto]

    static constraints = {
		descrizione(nullable:true)
		area(nullable:true)
    }
}
