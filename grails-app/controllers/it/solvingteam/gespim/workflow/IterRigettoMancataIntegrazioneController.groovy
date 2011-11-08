package it.solvingteam.gespim.workflow

import it.solvingteam.gespim.pratica.Pratica;

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.grails.activiti.ActivitiConstants;

class IterRigettoMancataIntegrazioneController {

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
