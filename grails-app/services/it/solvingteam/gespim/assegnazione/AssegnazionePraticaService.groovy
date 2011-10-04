package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.pratica.Pratica;
import it.solvingteam.gespim.storico.Storico;
import it.solvingteam.gespim.tipologiche.TipoOperazione;

class AssegnazionePraticaService {
	
	def springSecurityService

	static transactional = true

	Pratica processAssegnazioni(praticaInstance,params) {
		if (praticaInstance) {
			
			def user = springSecurityService.currentUser

			def listaAreeSelezionate = AreaCompetenza.getAll(params.areaId?.collect{ it as long })
			def listaAssegnazioniDaRimuovere = []
			//println listaAreeSelezionate
			//passo prima in rassegna quelle già presenti per vedere se son state rimosse ed
			//aggiornare quindi lo storico
			praticaInstance.assegnazioni?.each{assegnazione ->
				if(listaAreeSelezionate?.contains(assegnazione.areaCompetenza)){
					listaAreeSelezionate -= assegnazione.areaCompetenza
				}else{
					listaAssegnazioniDaRimuovere << assegnazione
				}
			}
			
			//rimuovo quelle 'sporche'
			listaAssegnazioniDaRimuovere.each{
				praticaInstance.removeFromAssegnazioni(it)
				it.delete()
				def storicoInstance = registraRimozioneAssegnazione(it,user)
				if (!storicoInstance.save()) {
					throw new AssegnazionePraticaException(message: "Registrazione Storico rimozione assegnazione ${it} non riuscita",
						praticaInstance:praticaInstance)
				}
			}
			
			//println ".....filtrate: "+listaAreeSelezionate
			//ora si inseriscono le nuove assegnazioni per ogni area selezionata
			listaAreeSelezionate?.each{
				def assegnazionePraticaInstance = new AssegnazionePratica(
						praticaAssegnata:praticaInstance,areaCompetenza:it,
						dataAssegnazione:new Date(),utenteAssegnante:user)
				//praticaInstance.addToAssegnazioni(assegnazionePraticaInstance)
				def storicoInstance = registraAssegnazione(assegnazionePraticaInstance,user)
				if (!assegnazionePraticaInstance.save() ||
					!storicoInstance.save()) {
					throw new AssegnazionePraticaException(message: "Assegnazione a ${it} non riuscita",
						praticaInstance:praticaInstance)
				}
			}
			return praticaInstance
		}
		throw new AssegnazionePraticaException(message: "Pratica inesistente.")
	}
	
	private Storico registraAssegnazione(assegnazionePraticaInstance,user){
		def storicoInstance = new Storico()
		storicoInstance.tipoOperazione = TipoOperazione.findByCodice(TipoOperazione.COD_ASSEGNAZIONE)
		storicoInstance.dataOperazione = new Date()
		storicoInstance.areaOperatore = user?.area?.toString()
		storicoInstance.utenteOperatore = user.toString()
		storicoInstance.areaAssegnataria = assegnazionePraticaInstance.areaCompetenza?.toString()
		
		storicoInstance
	}
	
	private Storico registraRimozioneAssegnazione(assegnazionePraticaInstance,user){
		def storicoInstance = new Storico()
		storicoInstance.tipoOperazione = TipoOperazione.findByCodice(TipoOperazione.COD_RIMOZIONE_ASSEGNAZIONE)
		storicoInstance.dataOperazione = new Date()
		storicoInstance.areaOperatore = user?.area?.toString()
		storicoInstance.utenteOperatore = user.toString()
		storicoInstance.areaAssegnataria = assegnazionePraticaInstance.areaCompetenza?.toString()
		
		storicoInstance
	}
	
	
}
class AssegnazionePraticaException extends RuntimeException {
	String message
	Pratica praticaInstance
}
