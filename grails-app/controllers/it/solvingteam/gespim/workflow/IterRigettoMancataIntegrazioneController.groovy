package it.solvingteam.gespim.workflow

import it.solvingteam.gespim.documentazione.Atto;
import it.solvingteam.gespim.documentazione.TipoAtto;
import it.solvingteam.gespim.pratica.Pratica;
import it.solvingteam.gespim.tipologiche.StatoPratica;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat;
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef;
import org.grails.activiti.ActivitiConstants;

class IterRigettoMancataIntegrazioneController {

	def jasperService
	def logo = servletContext.getRealPath("/images/logo-repubblica-italiana.gif")

	String sessionUsernameKey = CH.config.activiti.sessionUsernameKey?:ActivitiConstants.DEFAULT_SESSION_USERNAME_KEY

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static activiti = true

	def index = {
		redirect(action: "list", params: params)
	}

	def start = {
		println "...............eccomi"+params

		def iterRigettoMancataIntegrazioneInstance = new IterRigettoMancataIntegrazione()
		iterRigettoMancataIntegrazioneInstance.pratica = Pratica.get(params.idPratica as long)
		iterRigettoMancataIntegrazioneInstance.username = session[sessionUsernameKey]
		if (!iterRigettoMancataIntegrazioneInstance.save(flush: true)) {
			println "................errors "+iterRigettoMancataIntegrazioneInstance.errors
			redirect(action: "list")
			return
			//TODO : gestire
		}

		//start(params)
		params.username = session[sessionUsernameKey]
		params.id =  iterRigettoMancataIntegrazioneInstance.id
		activitiService.startProcess(params)
		redirect(controller:'task',action: "unassignedTaskList")

	}

	def sceltaoperazione = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		[iterRigettoMancataIntegrazioneInstance:iterRigettoMancataIntegrazioneInstance]
	}

	def stampa = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
			return
		}
		if (params.version) {
			def version = params.version.toLong()
			if (iterRigettoMancataIntegrazioneInstance.version > version) {

				iterRigettoMancataIntegrazioneInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
					message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')]
				as Object[], "Another user has updated this IterRigettoMancataIntegrazione while you were editing")
				render(view: "sceltaoperazione", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
				return
			}
		}
		iterRigettoMancataIntegrazioneInstance.properties = params
		if (!iterRigettoMancataIntegrazioneInstance.hasErrors() && iterRigettoMancataIntegrazioneInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), iterRigettoMancataIntegrazioneInstance.id])}"
			params.operazione = TipoOperazioneWF.STAMPA.name()
			println "..........................++ params:"+params
			completeTask(params)
			redirect(controller:'task',action: "allTaskList", id: iterRigettoMancataIntegrazioneInstance.id, params: [taskId:params.taskId, complete:true])
		}
		else {
			render(view: "sceltaoperazione", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
		}
	}

	def sceltaatto = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		[iterRigettoMancataIntegrazioneInstance:iterRigettoMancataIntegrazioneInstance]
	}

	def performSceltaAtto = {

		if(!params['attoId']){
			flash.message = "Selezionare atto."
			redirect(controller:'iterRigettoMancataIntegrazione',action: "sceltaatto",id:params.id)
			return
		}
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		if (params.version) {
			def version = params.version.toLong()
			if (iterRigettoMancataIntegrazioneInstance.version > version) {

				iterRigettoMancataIntegrazioneInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
					message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')]
				as Object[], "Another user has updated this IterRigettoMancataIntegrazione while you were editing")
				render(view: "sceltaatto", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
				return
			}
		}
		iterRigettoMancataIntegrazioneInstance.properties = params
		iterRigettoMancataIntegrazioneInstance.atto = Atto.get(params.attoId as long)
		if(iterRigettoMancataIntegrazioneInstance.atto.tipoAtto == TipoAtto.PREVIA_VIDIMAZIONE){
			iterRigettoMancataIntegrazioneInstance.pratica?.statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_IN_EVIDENZA)
		}
		if (!iterRigettoMancataIntegrazioneInstance.hasErrors() && iterRigettoMancataIntegrazioneInstance.save(flush: true)) {
			flash.message = "Operazione effettuata."
			params.tipoAtto = iterRigettoMancataIntegrazioneInstance.atto?.tipoAtto?.name()
			println "......................"+params
			completeTask(params)
			redirect(controller:'task',action: "allTaskList", id: iterRigettoMancataIntegrazioneInstance.id, params: [taskId:params.taskId, complete:true])
		}else {
			render(view: "sceltaatto", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
		}
	}

	def vidimazione = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		[iterRigettoMancataIntegrazioneInstance:iterRigettoMancataIntegrazioneInstance]
	}

	def apriAllegato = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		Atto attoInstance = Atto.get(params.idAtto as long)

		def parametersList = []
		parametersList << [
					Logo: logo,
					//TODO: DA MODIFICARE
					field : iterRigettoMancataIntegrazioneInstance.note
				]
		def reportDef = new JasperReportDef(
				name:attoInstance?.nomeTemplate,
				fileFormat:JasperExportFormat.PDF_FORMAT,
				reportData:parametersList,
				parameters:[:]
				)

		def outputStream = response.getOutputStream()
		byte[] bytes = jasperService.generateReport(reportDef).toByteArray()

		response.setContentType("application/pdf")
		//response.setContentLength(bytes.length)

		//response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=${attoInstance?.nomeTemplate}.pdf")

		response.outputStream << bytes
		/*
		 outputStream.write(bytes, 0, bytes.length)
		 outputStream.flush()
		 outputStream.close()
		 */
	}

	def performVidimazione = {

		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		if (params.version) {
			def version = params.version.toLong()
			if (iterRigettoMancataIntegrazioneInstance.version > version) {

				iterRigettoMancataIntegrazioneInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
					message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')]
				as Object[], "Another user has updated this IterRigettoMancataIntegrazione while you were editing")
				render(view: "vidimazione", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
				return
			}
		}
		iterRigettoMancataIntegrazioneInstance.properties = params
		if (!iterRigettoMancataIntegrazioneInstance.hasErrors() && iterRigettoMancataIntegrazioneInstance.save(flush: true)) {
			flash.message = "Operazione effettuata."
			Boolean isApprovato = params['_action_performVidimazione']?.equals('Approva')?:false
			if(!isApprovato){
				iterRigettoMancataIntegrazioneInstance.pratica?.statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_IN_LAVORAZIONE)
			}
			params.isApprovato = isApprovato
			params.username = iterRigettoMancataIntegrazioneInstance.username
			completeTask(params)
			redirect(controller:'task',action: "allTaskList", id: iterRigettoMancataIntegrazioneInstance.id, params: [taskId:params.taskId, complete:true])
		}else {
			render(view: "vidimazione", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
		}
	}

	def stampaatto = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		[iterRigettoMancataIntegrazioneInstance:iterRigettoMancataIntegrazioneInstance]
	}


	def performStampaAtto = {

		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "Elemento non trovato."
			redirect(controller: "task", action: "myTaskList")
			return
		}
		if (params.version) {
			def version = params.version.toLong()
			if (iterRigettoMancataIntegrazioneInstance.version > version) {

				iterRigettoMancataIntegrazioneInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
					message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')]
				as Object[], "Another user has updated this IterRigettoMancataIntegrazione while you were editing")
				render(view: "stampaatto", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
				return
			}
		}
		iterRigettoMancataIntegrazioneInstance.properties = params
		iterRigettoMancataIntegrazioneInstance.pratica?.statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_LAVORATA)
		if (!iterRigettoMancataIntegrazioneInstance.hasErrors() && iterRigettoMancataIntegrazioneInstance.save(flush: true)) {
			flash.message = "Operazione effettuata."
			completeTask(params)
			redirect(controller:'task',action: "allTaskList", id: iterRigettoMancataIntegrazioneInstance.id, params: [taskId:params.taskId, complete:true])
		}else {
			render(view: "stampaatto", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
		}
	}


	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[iterRigettoMancataIntegrazioneInstanceList: IterRigettoMancataIntegrazione.list(params),
					iterRigettoMancataIntegrazioneInstanceTotal: IterRigettoMancataIntegrazione.count(),
					myTasksCount: assignedTasksCount]
	}

	def create = {
		def iterRigettoMancataIntegrazioneInstance = new IterRigettoMancataIntegrazione()
		iterRigettoMancataIntegrazioneInstance.properties = params
		return [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance,
			myTasksCount: assignedTasksCount]
	}

	def save = {
		def iterRigettoMancataIntegrazioneInstance = new IterRigettoMancataIntegrazione(params)
		if (iterRigettoMancataIntegrazioneInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), iterRigettoMancataIntegrazioneInstance.id])}"
			params.id = iterRigettoMancataIntegrazioneInstance.id
			if (params.complete) {
				completeTask(params)
			} else {
				params.action="show"
				saveTask(params)
			}
			redirect(action: "show", params: params)
		}
		else {
			render(view: "create", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
		}
	}

	def show = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
		else {
			[iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount]
		}
	}

	def edit = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (!iterRigettoMancataIntegrazioneInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
		else {
			[iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount]
		}
	}

	def update = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (iterRigettoMancataIntegrazioneInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterRigettoMancataIntegrazioneInstance.version > version) {

					iterRigettoMancataIntegrazioneInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione')]
					as Object[], "Another user has updated this IterRigettoMancataIntegrazione while you were editing")
					render(view: "edit", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
					return
				}
			}
			iterRigettoMancataIntegrazioneInstance.properties = params
			if (!iterRigettoMancataIntegrazioneInstance.hasErrors() && iterRigettoMancataIntegrazioneInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), iterRigettoMancataIntegrazioneInstance.id])}"
				Boolean isComplete = params["_action_update"].equals(message(code: 'default.button.complete.label', default: 'Complete'))
				if (isComplete) {
					completeTask(params)
				} else {
					params.action="show"
					saveTask(params)
				}
				redirect(action: "show", id: iterRigettoMancataIntegrazioneInstance.id, params: [taskId:params.taskId, complete:isComplete?:null])
			}
			else {
				render(view: "edit", model: [iterRigettoMancataIntegrazioneInstance: iterRigettoMancataIntegrazioneInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}

	def delete = {
		def iterRigettoMancataIntegrazioneInstance = IterRigettoMancataIntegrazione.get(params.id)
		if (iterRigettoMancataIntegrazioneInstance) {
			try {
				iterRigettoMancataIntegrazioneInstance.delete(flush: true)
				deleteTask(params.taskId)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), params.id])}"
				redirect(controller: "task", action: "myTaskList")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), params.id])}"
				redirect(action: "show", id: params.id, params: params)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterRigettoMancataIntegrazione.label', default: 'IterRigettoMancataIntegrazione'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
}
