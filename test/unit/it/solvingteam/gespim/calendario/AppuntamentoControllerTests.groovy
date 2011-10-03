package it.solvingteam.gespim.calendario

import grails.test.*
import grails.converters.*
import org.joda.time.DateTime

class AppuntamentoControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAppuntamentiFeed() {
    
        Appuntamento.metaClass.static.listInRange = {long s, long e ->
             def l = []
             l << new Appuntamento()
             l << new Appuntamento()
             l << new Appuntamento()
             return l
        }

        controller.params.start = 1
        controller.params.end = 1
        controller.appuntamentiFeed()
        def json = controller.response.contentAsString
        def parsedJson = JSON.parse(json)

        assertEquals 3, parsedJson.appuntamento.size()
    }

    void testAppuntamentiFeedMissingParams() {
        controller.params.end = 1
        controller.appuntamentiFeed()
        assertEquals 400, controller.response.status
        def message = 'start and end parameters are mandatory'
        assertEquals message, controller.response.contentAsString
    }

    void testAppuntamentiFeedMissingStartParam() {
        controller.params.end = 1
        controller.appuntamentiFeed()
        assertEquals 400, controller.response.status
        assertEquals 'start and end parameters are mandatory', controller.response.contentAsString
    }

    void testAppuntamentiMissingEndParam() {
        controller.params.start = 1
        controller.appuntamentiFeed()
        assertEquals 400, controller.response.status
        assertEquals 'start and end parameters are mandatory', controller.response.contentAsString
    }

    void testMoveAppuntamento() {
        mockDomain(Appuntamento)
        def dataRiferimento = new DateTime()
        def appuntamento = new Appuntamento(oggetto:'oggetto',
                                            inizio: dataRiferimento,
                                            fine: dataRiferimento.plusMinutes(20))
        appuntamento.save()
        mockCommandObject(ModificaAppuntamentoCommand)
        def cmd = new ModificaAppuntamentoCommand(eventId:appuntamento.id,
                                              dayDelta: 1)
        cmd.validate()
        controller.moveAppuntamento(cmd)
        assertEquals 200, controller.response.status
        assertEquals 'OK', controller.response.contentAsString
        assertFalse dataRiferimento == appuntamento.inizio
    }

    void testMoveAppuntamentoMissingParams() {
        mockCommandObject(ModificaAppuntamentoCommand)
        def cmd = new ModificaAppuntamentoCommand()
        cmd.validate()
        controller.moveAppuntamento(cmd)
        assertEquals 400, controller.response.status
        assertEquals 'event id required', controller.response.contentAsString
    }
    
    void testResizeAppuntamento() {
        mockDomain(Appuntamento)
        def dataRiferimento = new DateTime()
        def appuntamento = new Appuntamento(oggetto:'oggetto',
                                            inizio: dataRiferimento,
                                            fine: dataRiferimento.plusMinutes(20))
        appuntamento.save()
        mockCommandObject(ModificaAppuntamentoCommand)
        def cmd = new ModificaAppuntamentoCommand(eventId:appuntamento.id,
                                              dayDelta: 1)
        cmd.validate()
        controller.resizeAppuntamento(cmd)
        assertEquals 200, controller.response.status
        assertEquals 'OK', controller.response.contentAsString
        assertFalse dataRiferimento.plusMinutes(20) == appuntamento.fine
    }

    void testResizeAppuntamentoMissingParams() {
        mockCommandObject(ModificaAppuntamentoCommand)
        def cmd = new ModificaAppuntamentoCommand()
        cmd.validate()
        controller.resizeAppuntamento(cmd)
        assertEquals 400, controller.response.status
        assertEquals 'event id required', controller.response.contentAsString
    }
}
