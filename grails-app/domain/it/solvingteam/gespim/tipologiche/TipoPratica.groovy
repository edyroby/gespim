package it.solvingteam.gespim.tipologiche

class TipoPratica {
	
	String codice
	String descrizione

    static constraints = {
		codice(blank:false,unique:true)
		descrizione(blank:false,unique:true)
    }
	
	@Override
	public String toString() {
		descrizione
	}
}
