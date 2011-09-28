package it.solvingteam.gespim.pratica

import grails.converters.JSON
import it.solvingteam.gespim.tipologiche.StatoPratica;
import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.TipologiaLegale;

class PraticaController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def search = {
	}

	def results = {PraticaCommand cmd ->
		println cmd.statoPratica
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def listaPratiche = Pratica.cercaPratiche(cmd,params)
		[listaPratiche:listaPratiche,listaPraticheTotal:listaPratiche.totalCount]
	}


	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[praticaInstanceList: Pratica.list(params), praticaInstanceTotal: Pratica.count()]
	}

	def create = {
		def praticaInstance = new Pratica()
		praticaInstance.properties = params
		return [praticaInstance: praticaInstance]
	}

	def save = {
		def praticaInstance = new Pratica(params)
		if (praticaInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'pratica.label', default: 'Pratica'), praticaInstance.id])}"
			redirect(action: "show", id: praticaInstance.id)
		}
		else {
			render(view: "create", model: [praticaInstance: praticaInstance])
		}
	}

	def show = {
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "list")
		}
		else {
			[praticaInstance: praticaInstance]
		}
	}
	
	def showDettaglioPratica = {
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "results")
		}
		else {
			[praticaInstance: praticaInstance]
		}
	}
	

	def edit = {
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [praticaInstance: praticaInstance]
		}
	}

	def update = {
		def praticaInstance = Pratica.get(params.id)
		if (praticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (praticaInstance.version > version) {

					praticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'pratica.label', default: 'Pratica')]
					as Object[], "Another user has updated this Pratica while you were editing")
					render(view: "edit", model: [praticaInstance: praticaInstance])
					return
				}
			}
			praticaInstance.properties = params
			if (!praticaInstance.hasErrors() && praticaInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pratica.label', default: 'Pratica'), praticaInstance.id])}"
				redirect(action: "showDettaglioPratica", id: praticaInstance.id)
			}
			else {
				render(view: "edit", model: [praticaInstance: praticaInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def praticaInstance = Pratica.get(params.id)
		if (praticaInstance) {
			try {
				praticaInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def assegnazione = {
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [praticaInstance: praticaInstance]
		}
	}


	def autocompleteResult = {
		String term = params.q

		def results = Beneficiario.withCriteria {
			or{
				ilike 'cognome', "%${term}%"
				ilike 'nome', "%${term}%"
			}
		}?.collect {
			[id:it.id, name:"${it.cognome} ${it.nome}"]
		}

		render results as JSON
	}
}
class PraticaCommand{
	String numeroPratica
	String codiceIstanza
	String codiceQuestura

	String tipoPratica

	String tipologiaLegale
	String statoPratica

	String nomeRichiedente
	String cognomeRichiedente
	Date dataNascitaRichiedente

	Set beneficiari = []as Set

	static constraints = {
		numeroPratica(nullable:true)
		codiceIstanza(nullable:true)
		codiceQuestura(nullable:true)
		statoPratica(nullable:true)
		tipologiaLegale(nullable:true)
		tipoPratica(nullable:false)
		nomeRichiedente(nullable:true)
		cognomeRichiedente(nullable:true)
		dataNascitaRichiedente(nullable:true)
		beneficiari(nullable:true)
	}
}
