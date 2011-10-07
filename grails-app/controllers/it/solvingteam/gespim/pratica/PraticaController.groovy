package it.solvingteam.gespim.pratica

import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat;
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;

import grails.converters.JSON
import it.solvingteam.gespim.assegnazione.AreaCompetenza;
import it.solvingteam.gespim.assegnazione.AssegnazionePratica
import it.solvingteam.gespim.security.Ruolo;
import it.solvingteam.gespim.tipologiche.StatoPratica;
import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.TipologiaLegale;
import it.solvingteam.gespim.tipologiche.TipoOperazione;
import it.solvingteam.gespim.storico.Storico;
import it.solvingteam.gespim.docobj.DocumentObject;

class PraticaController {

	def springSecurityService
	def jasperService
	def logo = servletContext.getRealPath("/images/logo-repubblica-italiana.gif")

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
	
	def presaInCarico = {
		redirect(controller:'assegnazionePratica',action:'presaInCarico',params:params)
	}
	
	def presaInCaricoMassiva = {
		redirect(controller:'assegnazionePratica',action:'presaInCaricoMassiva',params:params)
	}
	
	def searchStampa = {
	}
	
	def resultsForStampa = {PraticaCommand cmd ->
		
		if(!cmd.statoPratica || !cmd.tipologiaLegale || !cmd.tipoPratica){
			flash.message = "E' necessario selezionare tutte e tre le voci."
			redirect(action: "searchStampa")
			return
		}
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def user = springSecurityService.currentUser
		def listaPratiche = Pratica.cercaPratichePerStampe(cmd,user,params)
		[listaPratiche:listaPratiche,listaPraticheTotal:listaPratiche.totalCount]
	}
	
	def stampaMassiva = {
		
		if(!params['praticaId']){
			flash.error = "Nessuna voce selezionata"
			redirect(action: "resultsForStampa",params:params)
			return
		}
		
		def user = springSecurityService.currentUser
		
		def listaPratiche = []
		if(params['praticaId']?.size() == 1 ){
			listaPratiche << Pratica.get(params['praticaId'] as long)
		}else{
			listaPratiche += Pratica.getAll(params['praticaId']?.toList()?.collect{it as long})
		}
		
		def parametersList = []
		byte [] singleSave
		listaPratiche?.eachWithIndex{pratica,index ->
			def beneficiario = pratica.beneficiari?.toArray()[0]
			parametersList << [
					Logo: logo,
					BARCODE : "BARCODE",
					IdDocOA : pratica.numeroPratica,
					A_ProtocolloStampa : "XXXXXXXXXXXXX",
					Cognome : pratica.richiedente?.cognome,
					Nome : pratica.richiedente?.nome,
					IndirizzoResidenza : pratica.richiedente?.residenza,
					NumeroCivicoResidenza : "54",
					CAPResidenza : "00100",
					ComuneResidenza : pratica.richiedente?.residenza,
					LocalitaResidenza : pratica.richiedente?.residenza,
					SiglaProvinciaResidenza : pratica.richiedente?.provinciaResidenza,
					StatoResidenza : "STATO RESIDENZA",
					Informazione31 : beneficiario?.cognome,
					Informazione32 : beneficiario?.nome,
					Informazione33 : beneficiario?.dataNascita,
					Informazione34 : beneficiario?.cittadinanza,
					Data : "10/04/2004",
					DataAtto : new Date(),
					RigaFirma1 : "Santoriello Ferdinando"
					]
			
			
			
			//DA MODIFICARE
			def reportDefTemp = new JasperReportDef(
				name:"Art10Bis2",
				fileFormat:JasperExportFormat.PDF_FORMAT,
				reportData:[parametersList[index]],
				parameters:[:]
			)
			//TODO : NE GENERA DUE
			singleSave = jasperService.generateReport(reportDefTemp).toByteArray()
			DocumentObject docObj = new DocumentObject(idDocumentale:1,docName:"Articolo 10",dataCreazione:new Date(),fileAllegatoByteArray:singleSave)
			// TODO : DA RIVEDERE
			pratica.documenti?.each{
				it.delete()
			}
			pratica.documenti?.clear()
			pratica.addToDocumenti(docObj)
			registraEmissioneDecreto(pratica,user)
			//FINE
		}
		def reportDef = new JasperReportDef(
			name:"Art10Bis2",
			fileFormat:JasperExportFormat.PDF_FORMAT,
			reportData:parametersList,
			parameters:[:]
		)
		
		/*
		 * DocumentObject docObj = new DocumentObject(idDocumentale:result,docName:fileName,dataCreazione:new Date(),fileAllegatoByteArray:auc.allegati)
			argPariOppInstance.addToDocumentObject(docObj)
		 */
		
		def outputStream = response.getOutputStream()
		byte[] bytes = jasperService.generateReport(reportDef).toByteArray()
		
		response.setContentType("application/pdf")
		response.setContentLength(bytes.length)
		
		outputStream.write(bytes, 0, bytes.length)
		outputStream.flush()
		outputStream.close()
		
		
	}
	
	private Storico registraEmissioneDecreto(praticaInstance,user){
		def storicoInstance = new Storico()
		storicoInstance.numeroPratica = praticaInstance.numeroPratica
		storicoInstance.codiceIstanza = praticaInstance.codiceIstanza
		storicoInstance.codiceQuestura = praticaInstance.codiceQuestura
		storicoInstance.tipoOperazione = TipoOperazione.findByCodice(TipoOperazione.COD_DECRETO_EMESSO)
		storicoInstance.dataOperazione = new Date()
		storicoInstance.areaOperatore = user?.area?.toString()
		storicoInstance.utenteOperatore = user.toString()
		//storicoInstance.areaAssegnataria = assegnazionePraticaInstance.areaCompetenza?.toString()
		return storicoInstance?.save()
	}
	
	def apriAllegato = {
		
		def praticaInstance = Pratica.get(params.id)
		if (!praticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pratica.label', default: 'Pratica'), params.id])}"
			redirect(action: "search")
			return
		}
		
		DocumentObject docObj = DocumentObject.get(params.idDoc)
		if (!praticaInstance.documenti || !praticaInstance.documenti.contains(docObj)) {
			flash.message = "Documento non trovato."
			redirect(action: "search")
			return
		}
		
		
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=${docObj.docName}.pdf")
		
		response.outputStream << docObj.fileAllegatoByteArray
		
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
