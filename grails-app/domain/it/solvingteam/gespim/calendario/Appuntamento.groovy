package it.solvingteam.gespim.calendario

import org.joda.time.DateTime

class Appuntamento {

    String oggetto
    String note
    DateTime inizio
    DateTime fine

    static constraints = {
        oggetto(blank:false)
        note(nullable:true)
        inizio(nullable:false)
        fine(nullable:true)
    }

    static def listInRange(long startInstant, long endInstant) {
        DateTime dataInizio = new DateTime(startInstant)
        DateTime dataFine = new DateTime(endInstant)

        Appuntamento.findAllByInizioBetween(dataInizio, dataFine)
    }
    
}
