package it.solvingteam.gespim.storico

import it.solvingteam.gespim.tipologiche.TipoOperazione;

class Storico {
	
	TipoOperazione tipoOperazione
	Date dataOperazione
	
	//ASSEGNAZIONE
	String areaAssegnante
	String utenteAssegnante
	String areaAssegnataria
	boolean presaInCarico
	Date dataPresaInCarico
	String utentePresaInCarico
	String esito
	

    static constraints = {
		tipoOperazione(nullable:true)
		dataOperazione(nullable:true)
		areaAssegnante(nullable:true)
		utenteAssegnante(nullable:true)
		areaAssegnataria(nullable:true)
		dataPresaInCarico(nullable:true)
		utentePresaInCarico(nullable:true)
		esito(nullable:true)
    }
}
