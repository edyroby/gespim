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
				def storicoInstance = registraRimozioneAssegnazione(it,user)
				praticaInstance.removeFromAssegnazioni(it)
				it.delete()
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
		storicoInstance.numeroPratica = assegnazionePraticaInstance.praticaAssegnata.numeroPratica
		storicoInstance.codiceIstanza = assegnazionePraticaInstance.praticaAssegnata.codiceIstanza
		storicoInstance.codiceQuestura = assegnazionePraticaInstance.praticaAssegnata.codiceQuestura
		storicoInstance.tipoOperazione = TipoOperazione.findByCodice(TipoOperazione.COD_ASSEGNAZIONE)
		storicoInstance.dataOperazione = assegnazionePraticaInstance.dataAssegnazione
		storicoInstance.areaOperatore = user?.area?.toString()
		storicoInstance.utenteOperatore = user.toString()
		storicoInstance.areaAssegnataria = assegnazionePraticaInstance.areaCompetenza?.toString()
		
		storicoInstance
	}
	
	private Storico registraRimozioneAssegnazione(assegnazionePraticaInstance,user){
		def storicoInstance = new Storico()
		storicoInstance.numeroPratica = assegnazionePraticaInstance.praticaAssegnata.numeroPratica
		storicoInstance.codiceIstanza = assegnazionePraticaInstance.praticaAssegnata.codiceIstanza
		storicoInstance.codiceQuestura = assegnazionePraticaInstance.praticaAssegnata.codiceQuestura
		storicoInstance.tipoOperazione = TipoOperazione.findByCodice(TipoOperazione.COD_RIMOZIONE_ASSEGNAZIONE)
		storicoInstance.dataOperazione = assegnazionePraticaInstance.dataAssegnazione
		storicoInstance.areaOperatore = user?.area?.toString()
		storicoInstance.utenteOperatore = user.toString()
		storicoInstance.areaAssegnataria = assegnazionePraticaInstance.areaCompetenza?.toString()
		storicoInstance
	}
	
	//PRESA IN CARICA MULTIPLA DELLE ASSEGNAZIONI
	void presaInCaricaMultipla(params){
		
		def listaPratiche = []
		if(params['praticaId']?.size() == 1 ){
			listaPratiche << Pratica.get(params['praticaId'] as long)
		}else{
			listaPratiche += Pratica.getAll(params['praticaId']?.toList()?.collect{it as long})
		}
		println "....................lista pratiche: "+listaPratiche
		def c = AssegnazionePratica.createCriteria()
		def results = c.list(){
			'in'("praticaAssegnata",listaPratiche)
			eq("presaInCarico", false)
			eq("areaCompetenza",springSecurityService.currentUser.area)
		}
		
		def user = springSecurityService.currentUser

		results?.each{ assegnazionePraticaInstance ->
			assegnazionePraticaInstance.presaInCarico = true
			assegnazionePraticaInstance.dataPresaInCarico = new Date()
			assegnazionePraticaInstance.utentePresaInCarico = springSecurityService.currentUser
			def storicoInstance = registraPresaInCarico(assegnazionePraticaInstance,user)
			if (!assegnazionePraticaInstance.save() ||
				!storicoInstance?.save()) {
				throw new AssegnazionePraticaException(message: "Presa in carico a ${it} non riuscita",
					praticaInstance:praticaInstance)
			}
		}
		
	}
	private Storico registraPresaInCarico(assegnazionePraticaInstance,user){
		def c = Storico.createCriteria()
		def storicoInstance = c.get{
			eq 'tipoOperazione',TipoOperazione.findByCodice(TipoOperazione.COD_ASSEGNAZIONE)
			eq 'numeroPratica',assegnazionePraticaInstance.praticaAssegnata.numeroPratica
			eq 'codiceIstanza',assegnazionePraticaInstance.praticaAssegnata.codiceIstanza
			eq 'codiceQuestura',assegnazionePraticaInstance.praticaAssegnata.codiceQuestura
			eq 'dataOperazione',assegnazionePraticaInstance.dataAssegnazione
			eq 'presaInCarico',false
			isNull('dataPresaInCarico')
		}
		if(storicoInstance){
			storicoInstance.presaInCarico = true
			storicoInstance.dataPresaInCarico = new Date()
			storicoInstance.utentePresaInCarico = user
			return storicoInstance
		}
		return null
	}
	
	
}
class AssegnazionePraticaException extends RuntimeException {
	String message
	Pratica praticaInstance
}
