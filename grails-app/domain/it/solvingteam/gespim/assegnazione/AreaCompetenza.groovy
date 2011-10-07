package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.security.Utente

class AreaCompetenza {
	
	String codice
	String descrizione
	
	//CODICI
	static final String COD_AREA_LEGALE = "AC001"
	static final String COD_AREA_FLUSSI = "AC002"
	static final String COD_AREA_ART27 = "AC003"
	static final String COD_AREA_RIC_FAM = "AC004"
	static final String COD_AREA_CONV_STUD_LAV = "AC005"
	static final String COD_AREA_NEOCOMUNITARI = "AC006"
	static final String COD_AREA_FLUSSI_STAG = "AC007"
	static final String COD_AREA_SEGR_PERSONALE = "AC008"
	static final String COD_AREA_SEGR_DIRIGENTE = "AC009"
	
	static hasMany = [utenti:Utente]

    static constraints = {
		codice(blank:false,unique:true)
		descrizione(blank:false,unique:true)
		utenti(nullable:true)
    }
	
	boolean izAreaLegale(){
		return codice == COD_AREA_LEGALE
	}
	
	String toString(){
		descrizione
	}
}
