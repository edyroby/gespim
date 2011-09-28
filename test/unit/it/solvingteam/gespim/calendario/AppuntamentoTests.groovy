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
}
