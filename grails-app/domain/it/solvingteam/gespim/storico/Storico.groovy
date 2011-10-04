package it.solvingteam.gespim.storico

import it.solvingteam.gespim.tipologiche.TipoOperazione;

class Storico {
	
	TipoOperazione tipoOperazione
	String numeroPratica
	String codiceIstanza
	String codiceQuestura
	Date dataOperazione
	//area/ufficio dell'utente che esegue l'operazione
	String areaOperatore
	//l'utente che esegue l'operazione
	String utenteOperatore
	
	//ASSEGNAZIONE
	String areaAssegnataria
	boolean presaInCarico
	Date dataPresaInCarico
	String utentePresaInCarico
	String esito
	

    static constraints = {
		numeroPratica(nullable:true)
		codiceIstanza(nullable:true)
		codiceQuestura(nullable:true)
		tipoOperazione(nullable:true)
		dataOperazione(nullable:true)
		areaOperatore(nullable:true)
		utenteOperatore(nullable:true)
		areaAssegnataria(nullable:true)
		dataPresaInCarico(nullable:true)
		utentePresaInCarico(nullable:true)
		esito(nullable:true)
    }
}
