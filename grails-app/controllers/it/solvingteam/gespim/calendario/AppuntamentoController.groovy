package it.solvingteam.gespim.calendario

import grails.converters.*

class AppuntamentoController {

    def index = { }

    def appuntamentiFeed = {
        if (validateFeedParameters()) {
            response.status = 400
            render 'start and end parameters are mandatory'
            return 
        }
        
        def l = Appuntamento.listInRange(params.start, params.end)
        render l as JSON
    }

    private boolean validateFeedParameters() {
        !params.start || !params.end
    }
    
}
