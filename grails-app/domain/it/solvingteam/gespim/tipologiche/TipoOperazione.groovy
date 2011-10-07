package it.solvingteam.gespim.tipologiche

class TipoOperazione {
	
	String codice
	String descrizione
	
	static final String COD_ASSEGNAZIONE = "TOP001"
	static final String COD_RIMOZIONE_ASSEGNAZIONE = "TOP002"
	static final String COD_PRESA_IN_CARICO = "TOP003"

    static constraints = {
		codice(blank:false,unique:true)
		descrizione(blank:false,unique:true)
    }
}
