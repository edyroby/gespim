package it.solvingteam.gespim.pratica

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;

import grails.converters.JSON
import it.solvingteam.gespim.assegnazione.AreaCompetenza;
import it.solvingteam.gespim.assegnazione.AssegnazionePratica
import it.solvingteam.gespim.security.Ruolo;
import it.solvingteam.gespim.tipologiche.StatoPratica;
import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.TipologiaLegale;
import it.solvingteam.gespim.storico.Storico;

class PraticaController {

	def springSecurityService

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def search = {
	}

	def results = {PraticaCommand cmd ->
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def user = springSecurityService.currentUser
		def listaPratiche = Pratica.cercaPratiche(cmd,user,params)
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
			return
		}
		[praticaInstance: praticaInstance,authorized:isAuthorizedForPraticaOrLegale(praticaInstance,springSecurityService.currentUser)]
	}

	//controllo se la pratica risulta assegnata al mio ufficio
	//oppure se sono admin oppure se appartengo all'area legale
	private boolean isAuthorizedForPraticaOrLegale(praticaInstance,user){
		def result = AssegnazionePratica.findAssegnazioneByPraticaAndUtenza(praticaInstance,user)
		def authorized = (SpringSecurityUtils.ifAnyGranted("${Ruolo.ROLE_ADMIN},${Ruolo.ROLE_PROTOCOLLO}")
				|| result || user.area?.izAreaLegale())?true:false

		authorized
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
		redirect(controller:'assegnazionePratica',action:'assegnazione',params:params)
	}

	def storicoTab = {
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "search")
			return
		}
		
		params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
		def c = Storico.createCriteria()
		def storicoInstanceList = c.list(params){
			eq("numeroPratica",praticaInstance.numeroPratica)
		}
		
		[praticaInstance: praticaInstance,storicoInstanceList:storicoInstanceList,storicoInstanceTotal:storicoInstanceList.totalCount]
	}
	
	def presaInCaricoMultipla = {
		redirect(controller:'assegnazionePratica',action:'presaInCaricoMultipla',params:params)
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
	
	boolean assegnateAdUfficio

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
