package it.solvingteam.gespim.calendario

import grails.test.*
import org.joda.time.DateTime

class AppuntamentoTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSaveOk() {
        mockDomain(Appuntamento)
        def appuntamento = new Appuntamento()
        appuntamento.inizio = new DateTime()
        appuntamento.oggetto = "nuovo appuntamento"
        assert appuntamento.save()
    }
    
    void testToEventFormat() {
        def startDate = new DateTime()
        def endDate = new DateTime()

        def appuntamento = new Appuntamento()
        appuntamento.oggetto = "appuntamento"
        appuntamento.inizio = startDate
        appuntamento.fine = endDate

        mockDomain(Appuntamento, [appuntamento])

        def event = appuntamento.toEventFormat()
        assertEquals appuntamento.id, event.id
        assertEquals appuntamento.oggetto, event.title
        assertEquals appuntamento.inizio, event.start
        assertEquals appuntamento.fine, event.end
        assertEquals appuntamento.editable, event.editable
        
    }
}
