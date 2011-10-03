import it.solvingteam.gespim.tipologiche.StatoPratica
import it.solvingteam.gespim.tipologiche.TipoBeneficiario
import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.TipoRichiedente
import it.solvingteam.gespim.tipologiche.TipologiaLegale

import grails.util.GrailsUtil

class BootStrap {
	
	def fixtureLoader
	def grailsApplication

    def init = { servletContext ->
		if (GrailsUtil.environment != 'test') {
            prepareTipologiche()
            fixtureLoader.load('pratiche')
            fixtureLoader.load('richiedenti')
            fixtureLoader.load('appuntamenti_dev')
        }
    }
    def destroy = {
    }
	private void prepareTipologiche(){
		
		//Stato Pratica
		new StatoPratica(codice:StatoPratica.COD_STATO_NON_PRESENTE,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_NON_PRESENTE]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_LAVORATA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_LAVORATA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_IN_ATTESA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_IN_ATTESA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_IN_EVIDENZA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_IN_EVIDENZA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_ATTESA_PARERE_QUESTURA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_ATTESA_PARERE_QUESTURA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_ATTESA_PARERE_DPL,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_ATTESA_PARERE_DPL]).save()
		
		//Tipologia legale
		new TipologiaLegale(codice:TipologiaLegale.COD_ISTANZA_DI_RIESAME,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ISTANZA_DI_RIESAME]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RIGETTO_MANCATA_INTEGRAZIONE,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RIGETTO_MANCATA_INTEGRAZIONE]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_PARERE_NEGATIVO_QUESTURA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_PARERE_NEGATIVO_QUESTURA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_INAMMISSIBILITA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_INAMMISSIBILITA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RINUNCIA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RINUNCIA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_DISCONOSCIMENTO,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_DISCONOSCIMENTO]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_ASSENZA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ASSENZA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_ACCESSO_ATTI_DIFFIDE,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ACCESSO_ATTI_DIFFIDE]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RICORSI,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RICORSI]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_DOCUMENTAZIONE_FALSA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_DOCUMENTAZIONE_FALSA]).save()
		
		//TIPO PRATICHE
		grailsApplication.config.tipoPratiche?.each{
			new TipoPratica(codice:it.key,descrizione:it.value.nome).save()
		}
		//TIPO RICHIEDENTI
		grailsApplication.config.tipoRichiedenti?.each{
			new TipoRichiedente(codice:it.key,descrizione:it.value.nome).save()
		}
		//TIPO BENEFICIARIO
		grailsApplication.config.tipoBeneficiari?.each{
			new TipoBeneficiario(codice:it.key,descrizione:it.value.nome).save()
		}
		
	}
	
}
