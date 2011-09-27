package it.solvingteam.gespim.tipologiche

class TipoRichiedente {
	
	String codice
	String descrizione
	
	//CODICI
	static final String COD_DATORE = "TR01"
	static final String COD_AZIENDA = "TR02"
	static final String COD_RICONGIUNGIMENTO = "TR03"
	
	static constraints = {
		codice(blank:false,unique:true)
		descrizione(blank:false,unique:true)
	}

}
