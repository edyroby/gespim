package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.pratica.Pratica;
import it.solvingteam.gespim.storico.Storico;
import it.solvingteam.gespim.tipologiche.TipoOperazione;

class AssegnazionePraticaService {
	
	def springSecurityService

	static transactional = true

	Pratica processAssegnazioni(praticaInstance,params) {
		if (praticaInstance) {

			def listaAreeSelezionate = AreaCompetenza.getAll(params.areaId?.collect{ it as long })
			def listaAssegnazioniDaRimuovere = []
			println listaAreeSelezionate
			//passo prima in rassegna quelle già presenti per vedere se son stare rimosse ed
			//aggiornare quindi lo storico
			praticaInstance.assegnazioni?.each{assegnazione ->
				if(listaAreeSelezionate?.contains(assegnazione.areaCompetenza)){
					println ".....if: "+listaAreeSelezionate
					listaAreeSelezionate -= assegnazione.areaCompetenza
				}else{
					println ".....else: "+listaAreeSelezionate
					listaAssegnazioniDaRimuovere << assegnazione
					//TODO AGGIORNARE STORICO PER RIMOZIONE ASSEGNAZIONE. si può cancellare?
				}
			}
			
			//rimuovo quelle 'sporche'
			listaAssegnazioniDaRimuovere.each{
				praticaInstance.removeFromAssegnazioni(it)
				it.delete()
			}
			
			println ".....filtrate: "+listaAreeSelezionate
			//ora si inseriscono le nuove assegnazioni per ogni area selezionata
			listaAreeSelezionate?.each{
				def assegnazionePraticaInstance = new AssegnazionePratica(
						praticaAssegnata:praticaInstance,areaCompetenza:it,
						dataAssegnazione:new Date(),utenteAssegnante:springSecurityService.currentUser)
				//praticaInstance.addToAssegnazioni(assegnazionePraticaInstance)
				def storicoInstance = registraAssegnazione(assegnazionePraticaInstance,springSecurityService.currentUser)
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
		storicoInstance.areaAssegnante = user?.area?.toString()
		storicoInstance.utenteAssegnante = user.toString()
		storicoInstance.areaAssegnataria = assegnazionePraticaInstance.areaCompetenza?.toString()
		
		storicoInstance
	}
	
	
}
class AssegnazionePraticaException extends RuntimeException {
	String message
	Pratica praticaInstance
}
