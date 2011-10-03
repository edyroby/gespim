package it.solvingteam.gespim.tipologiche

class TipoOperazione {
	
	String codice
	String descrizione
	
	static final String COD_ASSEGNAZIONE = "TOP001"

    static constraints = {
		codice(blank:false,unique:true)
		descrizione(blank:false,unique:true)
    }
}
