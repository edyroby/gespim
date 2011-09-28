package it.solvingteam.gespim.calendario

import grails.test.*
import grails.converters.*

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
}
