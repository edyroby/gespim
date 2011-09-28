package it.solvingteam.gespim.calendario

import grails.test.*
import org.joda.time.DateTime

class AppuntamentoTests extends GroovyTestCase {

    def fixtureLoader

    protected void setUp() {
        super.setUp()
        fixtureLoader.load('appuntamenti')
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListAppuntamenti() {
        assert Appuntamento.count() > 1
    }

    void testListInRange() {
        def startInstant = new DateTime(2011, 5, 7, 0, 0, 0, 0).millis
        def endInstant = new DateTime(2011, 6, 14, 0, 0, 0, 0).millis
        assertEquals 3, Appuntamento.listInRange(startInstant, endInstant).size()
    }
}
