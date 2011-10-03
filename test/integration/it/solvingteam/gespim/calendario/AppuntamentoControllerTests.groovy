package it.solvingteam.gespim.calendario

import grails.test.*

class AppuntamentoControllerTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSaveAppuntamento() {
        def appuntamentoController = new AppuntamentoController()
        appuntamentoController.params.oggetto = 'oggetto'
        appuntamentoController.params.inizio = "" + new Date().time
        appuntamentoController.saveAppuntamento()
        assertEquals 200, appuntamentoController.response.status
        assertEquals 'OK', appuntamentoController.response.contentAsString
        assertEquals 1, Appuntamento.count()
    }

}
