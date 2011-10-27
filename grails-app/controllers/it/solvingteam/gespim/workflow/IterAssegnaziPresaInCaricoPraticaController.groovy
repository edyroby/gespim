package it.solvingteam.gespim.workflow

class IterAssegnaziPresaInCaricoPraticaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static activiti = true

    def index = {
        redirect(action: "list", params: params)
    }

    def start = {
        params.listaUtenti = ["admin","user"]
		activitiService.startProcess(params)
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
                    
                    iterAssegnaziPresaInCaricoPraticaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica')] as Object[], "Another user has updated this IterAssegnaziPresaInCaricoPratica while you were editing")
                    render(view: "edit", model: [iterAssegnaziPresaInCaricoPraticaInstance: iterAssegnaziPresaInCaricoPraticaInstance, myTasksCount: assignedTasksCount])
                    return
                }
            }
            iterAssegnaziPresaInCaricoPraticaInstance.properties = params
            if (!iterAssegnaziPresaInCaricoPraticaInstance.hasErrors() && iterAssegnaziPresaInCaricoPraticaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'iterAssegnaziPresaInCaricoPratica.label', default: 'IterAssegnaziPresaInCaricoPratica'), iterAssegnaziPresaInCaricoPraticaInstance.id])}"
								Boolean isComplete = params["_action_update"].equals(message(code: 'default.button.complete.label', default: 'Complete'))
								if (isComplete) {
										completeTask(params)
								} else {
										params.action="show"
										saveTask(params)
								}				
                redirect(action: "show", id: iterAssegnaziPresaInCaricoPraticaInstance.id, params: [taskId:params.taskId, complete:isComplete?:null])
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
