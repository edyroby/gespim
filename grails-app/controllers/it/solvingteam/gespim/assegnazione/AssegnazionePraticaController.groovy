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
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "list")
			return
		}
		return [praticaInstance: praticaInstance,areeMap:buildAree(praticaInstance)]
	}

	private Map buildAree(praticaInstance) {

		List aree = AreaCompetenza.list()
		Set areeCodes = []
		for (assegnazione in praticaInstance?.assegnazioni) {
			areeCodes << assegnazione.areaCompetenza?.codice
		}
		LinkedHashMap<AreaCompetenza, Boolean> areeMap = [:]
		for (area in aree) {
			areeMap[(area)] = areeCodes.contains(area.codice)
		}

		areeMap
	}

	def confermaAssegnazione = {

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

	}
	
	def backToDettaglioPratica = {
		redirect(controller:'pratica',action: "showDettaglioPratica",id:params.id)
	}
}
