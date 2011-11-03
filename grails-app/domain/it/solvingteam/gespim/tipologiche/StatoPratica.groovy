package it.solvingteam.gespim.tipologiche

class StatoPratica {

   //DESCRIZIONI
	static final String STATO_NON_PRESENTE = 'Non presente'
	static final String STATO_LAVORATA = 'Lavorata'
	static final String STATO_IN_ATTESA = 'Attesa Risposta'
	static final String STATO_IN_EVIDENZA = 'In Evidenza'
	static final String STATO_ATTESA_PARERE_QUESTURA = 'Attesa Parere Questura'
	static final String STATO_ATTESA_PARERE_DPL  = 'Attesa Parere DPL'
	static final String STATO_CONVOCATA  = 'Convocata'
	static final String STATO_IN_LAVORAZIONE = 'In Lavorazione'
	
	//CODICI
	static final String COD_STATO_NON_PRESENTE = '000'
	static final String COD_STATO_LAVORATA = '001'
	static final String COD_STATO_IN_ATTESA = '002'
	static final String COD_STATO_IN_EVIDENZA = '003'
	static final String COD_STATO_ATTESA_PARERE_QUESTURA = '004'
	static final String COD_STATO_ATTESA_PARERE_DPL = '005'
	static final String COD_STATO_CONVOCATA  = '006'
	static final String COD_STATO_IN_LAVORAZIONE = '007'
	
	
	//MAPPA CODICI/DESCRIZIONI
	static final mappaCodici = [
		(COD_STATO_NON_PRESENTE):STATO_NON_PRESENTE,
		(COD_STATO_LAVORATA):STATO_LAVORATA,
		(COD_STATO_IN_ATTESA):STATO_IN_ATTESA,
		(COD_STATO_IN_EVIDENZA):STATO_IN_EVIDENZA,
		(COD_STATO_ATTESA_PARERE_QUESTURA):STATO_ATTESA_PARERE_QUESTURA,
		(COD_STATO_ATTESA_PARERE_DPL):STATO_ATTESA_PARERE_DPL,
		(COD_STATO_CONVOCATA):STATO_CONVOCATA,
		(COD_STATO_IN_LAVORAZIONE):STATO_IN_LAVORAZIONE
	]
	
	
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
