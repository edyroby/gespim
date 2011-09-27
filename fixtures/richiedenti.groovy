import it.solvingteam.gespim.pratica.Richiedente;
import it.solvingteam.gespim.tipologiche.TipoRichiedente;

import java.util.Date;


fixture {
	azienda1(Richiedente,ragioneSociale:'azienda A',partitaIVA:'456251',viaSedeLegale:'via dei gigli'){
		tipoRichiedente = TipoRichiedente.findByCodice(TipoRichiedente.COD_AZIENDA)
	}
	azienda2(Richiedente,ragioneSociale:'azienda B',partitaIVA:'45484',viaSedeLegale:'via dei fori'){
		tipoRichiedente = TipoRichiedente.findByCodice(TipoRichiedente.COD_AZIENDA)
	}
	azienda3(Richiedente,ragioneSociale:'azienda C',partitaIVA:'78454',viaSedeLegale:'via del mouse'){
		tipoRichiedente = TipoRichiedente.findByCodice(TipoRichiedente.COD_AZIENDA)
	}
	
	
	
	datore1(Richiedente,nome:'Mario',cognome:'Bianchi',dataNascita: new Date(),cittadinanza:'italiana',residenza:'Roma',provinciaResidenza:'RM'){
		tipoRichiedente = TipoRichiedente.findByCodice(TipoRichiedente.COD_DATORE)
	}
	datore2(Richiedente,nome:'Maria',cognome:'Bozzi',dataNascita: new Date(),cittadinanza:'italiana',residenza:'Napoli',provinciaResidenza:'NA'){
		tipoRichiedente = TipoRichiedente.findByCodice(TipoRichiedente.COD_DATORE)
	}
	datore3(Richiedente,nome:'Giuseppe',cognome:'Rossi',dataNascita: new Date(),cittadinanza:'italiana',residenza:'Modena',provinciaResidenza:'MO'){
		tipoRichiedente = TipoRichiedente.findByCodice(TipoRichiedente.COD_DATORE)
	}
	
	richRicong1(Richiedente,nome:'Aron',cognome:'Karter',dataNascita: new Date(),cittadinanza:'argentina',residenza:'Bari',provinciaResidenza:'BA'){
		tipoRichiedente = TipoRichiedente.findByCodice(TipoRichiedente.COD_RICONGIUNGIMENTO)
	}
	
}