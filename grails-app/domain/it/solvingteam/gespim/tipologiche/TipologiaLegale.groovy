package it.solvingteam.gespim.tipologiche

/*
 * questa classe rappresenta le tipologie di attività
 * che l'ufficio legale può operare sulle pratiche.
 * E' in pratica una classificazione della pratica
 * dal punto di vista del contenzioso.
 */
class TipologiaLegale {

	String descrizione
	String codice
	
	//DESCRIZIONI
	static final String ISTANZA_DI_RIESAME = "Istanza di Riesame"
	static final String RIGETTO_MANCATA_INTEGRAZIONE = "Rigetto per Mancata Integrazione"
	static final String PARERE_NEGATIVO_QUESTURA = "Parere Negativo Questura"
	static final String INAMMISSIBILITA = "Inammissibilita'"
	static final String RINUNCIA = "Rinuncia"
	static final String DISCONOSCIMENTO = "Disconoscimento"
	static final String ASSENZA = "Assenza"
	static final String ACCESSO_ATTI_DIFFIDE = "Accesso Atti e Diffide"
	static final String RICORSI = "Ricorsi"
	static final String DOCUMENTAZIONE_FALSA = "Documentazione Falsa"
	
	//CODICI
	static final String COD_ISTANZA_DI_RIESAME = "001"
	static final String COD_RIGETTO_MANCATA_INTEGRAZIONE = "002"
	static final String COD_PARERE_NEGATIVO_QUESTURA = "003"
	static final String COD_INAMMISSIBILITA = "004"
	static final String COD_RINUNCIA = "005"
	static final String COD_DISCONOSCIMENTO = "006"
	static final String COD_ASSENZA = "007"
	static final String COD_ACCESSO_ATTI_DIFFIDE = "008"
	static final String COD_RICORSI = "009"
	static final String COD_DOCUMENTAZIONE_FALSA = "010"
	
	//MAPPA CODICI/DESCRIZIONI
	static final mappaCodici = [
		(COD_ISTANZA_DI_RIESAME):ISTANZA_DI_RIESAME,
		(COD_RIGETTO_MANCATA_INTEGRAZIONE):RIGETTO_MANCATA_INTEGRAZIONE,
		(COD_PARERE_NEGATIVO_QUESTURA):PARERE_NEGATIVO_QUESTURA,
		(COD_INAMMISSIBILITA):INAMMISSIBILITA,
		(COD_RINUNCIA):RINUNCIA,
		(COD_DISCONOSCIMENTO):DISCONOSCIMENTO,
		(COD_ASSENZA):ASSENZA,
		(COD_ACCESSO_ATTI_DIFFIDE):ACCESSO_ATTI_DIFFIDE,
		(COD_RICORSI):RICORSI,
		(COD_DOCUMENTAZIONE_FALSA):DOCUMENTAZIONE_FALSA
	]
	
	
	static constraints = {
		descrizione(blank:false,unique:true)
		codice(blank:false,unique:true)
	}
	
	@Override
	public String toString() {
		descrizione
	}
	
	
}

