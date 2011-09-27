package it.solvingteam.gespim.pratica

import it.solvingteam.gespim.tipologiche.TipoRichiedente;

import java.util.Date;

class Richiedente {
	
	String ragioneSociale
	String partitaIVA
	String viaSedeLegale
	
	String nome
	String cognome
	Date dataNascita
	String cittadinanza
	String residenza
	String provinciaResidenza
	
	TipoRichiedente tipoRichiedente

    static constraints = {
		ragioneSociale(nullable:true)
		partitaIVA(nullable:true)
		viaSedeLegale(nullable:true)
		
		nome(nullable:true)
		cognome(nullable:true)
		dataNascita(nullable:true)
		cittadinanza(nullable:true)
		residenza(nullable:true)
		provinciaResidenza(nullable:true)
		
		tipoRichiedente(nullable:true)
    }
	
	String toString(){
		tipoRichiedente?.codice == TipoRichiedente.COD_DATORE ? (nome+" "+cognome) :ragioneSociale
	}
	
	
}
