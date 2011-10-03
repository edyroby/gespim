package it.solvingteam.gespim.calendario

import grails.converters.*

class AppuntamentoController {

    def index = { }

    def appuntamentiFeed = {
        if (!validateFeedParameters()) {
            response.status = 400
            render 'start and end parameters are mandatory'
            return 
        }
        
        def appuntamenti = Appuntamento.listInRange((params.start as long) * 1000, (params.end as long) * 1000)

        render appuntamenti.collect {
            it.toEventFormat()
        } as JSON
    }

    private boolean validateFeedParameters() {
        params.start && params.end
    }

    def saveAppuntamento = {
        def appuntamento = new Appuntamento(params)
        appuntamento.save()
        println appuntamento.errors
        render 'OK'
    }

    def moveAppuntamento = {ModificaAppuntamentoCommand cmd ->
        if (cmd.hasErrors()) {
            response.status = 400
            render 'event id required'
            return
        }
        def appuntamento = Appuntamento.get(cmd.eventId)
        move(appuntamento, cmd)
        render 'OK'
    }

    private void move(appuntamento,cmd) {
        appuntamento.inizio = appuntamento.inizio.plusDays(cmd.dayDelta).plusMinutes(cmd.minuteDelta)
        if(appuntamento.fine) {
            appuntamento.fine = appuntamento.fine.plusDays(cmd.dayDelta).plusMinutes(cmd.minuteDelta)
        }
    }

    def resizeAppuntamento = {ModificaAppuntamentoCommand cmd ->
        if (cmd.hasErrors()) {
            response.status = 400
            render 'event id required'
            return
        }
        def appuntamento = Appuntamento.get(cmd.eventId)
        resize(appuntamento, cmd)
        render 'OK'
    }

    private void resize(appuntamento,cmd) {
        if(appuntamento.fine) {
            appuntamento.fine = appuntamento.fine.plusDays(cmd.dayDelta).plusMinutes(cmd.minuteDelta)
        }
    }
}

class ModificaAppuntamentoCommand {
    Long eventId
    int dayDelta
    int minuteDelta
    
    static constraints = {
        eventId(nullable:false)
    }
}
