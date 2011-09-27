package it.solvingteam.gespim.tipologiche

class TipoBeneficiario {
	
	String codice
	String descrizione
	
	//CODICI
	static final String COD_LAVORATORE = "TB01"
	static final String COD_STRANIERO = "TB02"
	static final String COD_RICONGIUNTO = "TB03"
	
	static constraints = {
		codice(blank:false,unique:true)
		descrizione(blank:false,unique:true)
	}
	
}
