package it.solvingteam.gespim.workflow
import java.util.Map;

import it.solvingteam.gespim.security.Utente;
import it.solvingteam.gespim.tipologiche.StatoPratica;
import it.solvingteam.gespim.tipologiche.TipologiaLegale;

import it.solvingteam.gespim.assegnazione.AreaCompetenza;
import it.solvingteam.gespim.pratica.Pratica;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.grails.activiti.ActivitiConstants;

class IterAssegnaziPresaInCaricoPraticaController {

	def springSecurityService

	String sessionUsernameKey = CH.config.activiti.sessionUsernameKey?:ActivitiConstants.DEFAULT_SESSION_USERNAME_KEY

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static activiti = true

	def index = {
		redirect(action: "list", params: params)
	}

	def start = {
		
		if(!params.areaId){
			flash.message = "Nessuna voce selezionata."
			redirect(controller:'assegnazionePratica',action:'assegnazione',id:params.idPratica)
			return
		}
		
		def listaAreeSelezionate = AreaCompetenza.getAll(params.areaId?.collect{ it as long })

		def iterAssegnaziPresaInCaricoPraticaInstance = new IterAssegnaziPresaInCaricoPratica()
		//iterAssegnaziPresaInCaricoPraticaInstance.taskId = task.id
		iterAssegnaziPresaInCaricoPraticaInstance.pratica = Pratica.get(params.idPratica as long)
		if (!iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
			println "................errors "+iterAssegnaziPresaInCaricoPraticaInstance.errors
			redirect(action: "list")
			return
			//TODO : gestire
		}
		
		def listaResponsabiliAreeSelezionate = []
		listaAreeSelezionate?.each{area->
			listaResponsabiliAreeSelezionate += Utente.findAllByAreaAndResponsabile(area,true)
		}
		
		params.listaUtenti = listaResponsabiliAreeSelezionate?.collect{it.username}
		params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
		ProcessInstance pi = activitiService.startProcess(params)
		Task task = activitiService.getUnassignedTask(session[sessionUsernameKey], pi.id)
		redirect(controller:'task',action: "unassignedTaskList")
		
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[iterAssegnaziPresaInCaricoPraticaInstanceList: IterAssegnaziPresaInCaricoPratica.list(params),
					iterAssegnaziPresaInCaricoPraticaInstanceTotal: IterAssegnaziPresaInCaricoPratica.count(),
					myTasksCount: assignedTasksCount]
	}

	def create = {
		def iterAssegnaziPresaInCaricoPraticaInstance = new IterAssegnaziPresaInCaricoPratica()
		iterAssegnaziPresaInCaricoPraticaInstance.properties = params
		return [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance,
			myTasksCount: assignedTasksCount]
	}

	def save = {
		def iterAssegnaziPresaInCaricoPraticaInstance = new IterAssegnaziPresaInCaricoPratica(params)
		if (iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
			params.id = iterAssegnaziPresaInCaricoPraticaInstance.id
			if (params.complete) {
				completeTask(params)
			} else {
				params.action="show"
				saveTask(params)
			}
			redirect(action: "show", params: params)
		}
		else {
			render(view: "create", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
		}
	}

	def show = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (!iterAssegnaziPresaInCaricoPraticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
		else {
			[iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount]
		}
	}

	def smistamento = {
		println"....................smistamento"+params
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		def user = springSecurityService.currentUser
		def listaUtenti = Utente.findAllByAreaAndResponsabile(user.area,false)
		[iterAssegnaziPresaInCaricoPraticaInstance:iterAssegnaziPresaInCaricoPraticaInstance,listaUtenti:listaUtenti-user]
	}
	
	def esamefascicolo = {
		println "..................esamefascicolo "+params
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		[iterAssegnaziPresaInCaricoPraticaInstance:iterAssegnaziPresaInCaricoPraticaInstance]
	}
	
	def lavorazione = {
		println "..................lavorazione "+params
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		[iterAssegnaziPresaInCaricoPraticaInstance:iterAssegnaziPresaInCaricoPraticaInstance]
	}

	def edit = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (!iterAssegnaziPresaInCaricoPraticaInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
		else {
			[iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount]
		}
	}

	def update = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterAssegnaziPresaInCaricoPraticaInstance.version > version) {

					iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')]
					as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
					render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
					return
				}
			}


			iterAssegnaziPresaInCaricoPraticaInstance.properties = params
			if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
				params.username = iterAssegnaziPresaInCaricoPraticaInstance.username
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
				Boolean isComplete = params["_action_update"].equals(message(code: 'default.button.complete.label', default: 'Complete'))
				if (isComplete) {
					params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
					completeTask(params)
				} else {
					params.action="show"
					saveTask(params)
				}
				redirect(controller:'task',action: "allTaskList", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:isComplete?:null])
			}
			else {
				render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
	
	def performSmistamento = {
		if(!params.username){
			flash.message = "Selezionare almeno un Utenza."
			redirect(action: "smistamento",id:params.id,params:[taskId:params.taskId])
			return
		}
		
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterAssegnaziPresaInCaricoPraticaInstance.version > version) {

					iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')]
					as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
					render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
					return
				}
			}


			iterAssegnaziPresaInCaricoPraticaInstance.properties = params
			if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
				params.username = iterAssegnaziPresaInCaricoPraticaInstance.username
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
				//Boolean isComplete = params["_action_update"].equals(message(code: 'default.button.complete.label', default: 'Complete'))
				//if (isComplete) {
					params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
					completeTask(params)
				/*
					} else {
					params.action="show"
					saveTask(params)
				}
				*/
				redirect(controller:'task',action: "allTaskList", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:true])
			}
			else {
				render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
	
	
	def performEsameFascicolo = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterAssegnaziPresaInCaricoPraticaInstance.version > version) {

					iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')]
					as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
					render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
					return
				}
			}
 

			iterAssegnaziPresaInCaricoPraticaInstance.properties = params
			iterAssegnaziPresaInCaricoPraticaInstance.pratica.statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_IN_LAVORAZIONE)
			if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
				params.username = iterAssegnaziPresaInCaricoPraticaInstance.username
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
				/*
				Boolean isComplete = params["performEsameFascicolo"].equals(message(code: 'default.button.complete.label', default: 'Complete'))
				if (isComplete) {
				*/
					params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
					params.isPertinente = true
					completeTask(params)
				/*
				} 
				else {
					params.action="show"
					saveTask(params)
				}
				*/
				redirect(controller:'task',action: "allTaskList", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:true])
			}
			else {
				render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
	
	def riassegna = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterAssegnaziPresaInCaricoPraticaInstance.version > version) {

					iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')]
					as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
					render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
					return
				}
			}
 

			iterAssegnaziPresaInCaricoPraticaInstance.properties = params
			if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
				params.username = iterAssegnaziPresaInCaricoPraticaInstance.username
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
				/*
				Boolean isComplete = params["performEsameFascicolo"].equals(message(code: 'default.button.complete.label', default: 'Complete'))
				if (isComplete) {
				*/
					params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
					params.isPertinente = false
					completeTask(params)
				/*
				} 
				else {
					params.action="show"
					saveTask(params)
				}
				*/
				redirect(controller:'task',action: "allTaskList", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:false])
			}
			else {
				render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
	
	def sequestro = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterAssegnaziPresaInCaricoPraticaInstance.version > version) {

					iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')]
					as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
					render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
					return
				}
			}

			//iterAssegnaziPresaInCaricoPraticaInstance.properties = params
			//TODO: sequestro?
			//iterAssegnaziPresaInCaricoPraticaInstance.pratica.statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_LAVORATA)
			if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
				params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
				completeTask(params)
				redirect(action: "show", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:true])
			}
			else {
				render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
	
	def evidenza = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterAssegnaziPresaInCaricoPraticaInstance.version > version) {

					iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')]
					as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
					render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
					return
				}
			}

			//iterAssegnaziPresaInCaricoPraticaInstance.properties = params
			//TODO: evidenza?
			//iterAssegnaziPresaInCaricoPraticaInstance.pratica.statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_LAVORATA)
			if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
				params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
				completeTask(params)
				redirect(action: "show", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:true])
			}
			else {
				render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
	
	def performSceltaTipologia = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (iterAssegnaziPresaInCaricoPraticaInstance.version > version) {

					iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')]
					as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
					render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
					return
				}
			}

			//iterAssegnaziPresaInCaricoPraticaInstance.properties = params
			//iterAssegnaziPresaInCaricoPraticaInstance.pratica.statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_LAVORATA)
			iterAssegnaziPresaInCaricoPraticaInstance.pratica.tipologiaLegale = TipologiaLegale.get(params.tipologiaLegaleId as long)
			//TODO GESTIRE ADEGUATAMENTE
			def controllerTipologiaSelezionata = "iterRigettoMancataIntegrazione"
			if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
				params.id =  iterAssegnaziPresaInCaricoPraticaInstance.id
				completeTask(params)
				//TODO : QUI DOVREBBE ESSERCI UN REDIRECT ALLA ACTION CHE SANCISCE L'INIZIO DEL NUOVO WORKFLOW
				//redirect(action: "show", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:true])
				redirect(controller: controllerTipologiaSelezionata,action:"start",  params: [idPratica:iterAssegnaziPresaInCaricoPraticaInstance.pratica?.id])
				return
			}
			else {
				render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}

	def delete = {
		def iterAssegnaziPresaInCaricoPraticaInstance = IterAssegnaziPresaInCaricoPratica.get(params.id)
		if (iterAssegnaziPresaInCaricoPraticaInstance) {
			try {
				iterAssegnaziPresaInCaricoPraticaInstance.delete(flush: true)
				deleteTask(params.taskId)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
				redirect(controller: "task", action: "myTaskList")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
				redirect(action: "show", id: params.id, params: params)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), params.id])}"
			redirect(controller: "task", action: "myTaskList")
		}
	}
}
