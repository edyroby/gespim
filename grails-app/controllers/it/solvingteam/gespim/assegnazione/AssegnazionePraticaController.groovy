package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.pratica.Pratica;

import java.util.Map;

class AssegnazionePraticaController {

	def springSecurityService
	def assegnazionePraticaService

	def index = {
	}

	def assegnazione = {
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(controller:'pratica',action: "search")
			return
		}
		return [praticaInstance: praticaInstance,areeMap:buildAree(praticaInstance)]
	}

	private Map buildAree(praticaInstance) {

		List aree = AreaCompetenza.list()
		//preparo una mappa con key=codice area e value=boolean che 
		//dice se la partica è stata presa in carica dall'area assegnata
		LinkedHashMap<String, Boolean> mapCodesAndPreseInCarico = [:]
		for (assegnazione in praticaInstance?.assegnazioni) {
			mapCodesAndPreseInCarico[(assegnazione.areaCompetenza?.codice)] = assegnazione.presaInCarico
		}
		//preparo una mappa con key=area competenza e value=un expando con due boolean
		//il primo mi dice se è presente un assegnazione per quell'area
		//il secondo mi dice se è stata presa in carico
		LinkedHashMap<AreaCompetenza, Expando> areeMap = [:]
		for (area in aree) {
			def booleans = new Expando()
			booleans.checked = mapCodesAndPreseInCarico.containsKey(area.codice)
			if(booleans.checked){
				booleans.presaInCarico = mapCodesAndPreseInCarico[(area.codice)]
			}else{
				booleans.presaInCarico = false
			}
			areeMap[(area)] = booleans
		}
		areeMap
	}

	def confermaAssegnazione = {
		redirect(controller:'iterAssegnaziPresaInCaricoPratica',action: 'start')
	
/*
		try {
			
			def praticaInstance = Pratica.get(params.id)
			assegnazionePraticaService.processAssegnazioni(praticaInstance,params)
			flash.message = "Assegnazione effettuata con successo."
			redirect(controller:'pratica',action: "showDettaglioPratica",id:params.id)
			return
		} catch (Exception ex) {
			flash.error = ex.message
			render(view:'assegnazione',model:[praticaInstance:ex.praticaInstance,areeMap:buildAree(ex.praticaInstance)])
		}
*/
	}
	
	def backToDettaglioPratica = {
		redirect(controller:'pratica',action: "showDettaglioPratica",id:params.id)
	}
	
	def presaInCarico = {
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.error = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(controller:'pratica',action: "search")
			return
		}
		def user = springSecurityService.currentUser
		def assegnazionePraticaInstance = AssegnazionePratica.withCriteria(uniqueResult:true){
			eq 'praticaAssegnata',praticaInstance
			eq 'areaCompetenza',user.area
		}
		if(!assegnazionePraticaInstance){
			flash.error = "Assegnazione Pratica non presente."
			redirect(controller:'pratica',action: "showDettaglioPratica",id:params.id)
			return
		}
		if(assegnazionePraticaInstance.presaInCarico){
			flash.error = "La pratica risulta gia' presa in carico."
			redirect(controller:'pratica',action: "showDettaglioPratica",id:params.id)
			return
		}
		try {
			assegnazionePraticaService.presaInCaricoSingola(assegnazionePraticaInstance,user)
			flash.message = "Presa in carico effettuata con successo."
			redirect(controller:'pratica',action: "showDettaglioPratica",id:params.id)
			return
		} catch (Exception ex) {
			flash.error = ex.message
			redirect(controller:'pratica',action: "showDettaglioPratica",id:params.id)
		}
	}
	
	def presaInCaricoMassiva = {
		
		if(!params['praticaId']){
			flash.error = "Nessuna voce selezionata"
			redirect(controller:'pratica',action: "results",params:params)
			return
		}
		
		def user = springSecurityService.currentUser
		
		def listaPratiche = []
		if(params['praticaId']?.size() == 1 ){
			listaPratiche << Pratica.get(params['praticaId'] as long)
		}else{
			listaPratiche += Pratica.getAll(params['praticaId']?.toList()?.collect{it as long})
		}
		
		def assegnazionePraticaInstanceList = AssegnazionePratica.withCriteria{
			'in'("praticaAssegnata",listaPratiche)
			eq("presaInCarico", false)
			eq("areaCompetenza",user.area)
		}
		[assegnazionePraticaInstanceList:assegnazionePraticaInstanceList]
	}
	
	def confermaPresaInCaricoMassiva = {
		
		def listaIdAssegnaz = params.findAll{
			it.key.startsWith('_assegnaz_')
			}?.values()?.collect{it as long}
			
		if(!listaIdAssegnaz){
			flash.error = "Nessuna voce selezionata"
			render(view:'presaInCaricoMassiva')
			return
		}
		def listaAssegnazioni = AssegnazionePratica.getAll(listaIdAssegnaz)
		
		if(!listaAssegnazioni){
			flash.error = "Nessuna voce selezionata"
			redirect(controller:'pratica',action: "results")
			return
		}
		
		try {
			assegnazionePraticaService.presaInCaricoMassiva(listaAssegnazioni)
			flash.message = "Presa in carico effettuata con successo."
			redirect(controller:'pratica',action: "results")
			return
		} catch (Exception ex) {
			flash.error = ex.message
			render(view:'presaInCaricoMassiva',model:[assegnazionePraticaInstanceList:listaAssegnazioni*.refresh()])
		}
	}
	
}
