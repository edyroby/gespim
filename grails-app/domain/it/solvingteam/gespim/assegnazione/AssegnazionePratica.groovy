package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.pratica.Pratica;

class AssegnazionePratica {
	
	Pratica praticaAssegnata
	AreaCompetenza areaCompetenza
	boolean presaInCarico
	

    static constraints = {
		praticaAssegnata(nullable:false)
		areeDiCompetenza(nullable:false)
    }
}
