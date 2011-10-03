import it.solvingteam.gespim.calendario.Appuntamento
import org.joda.time.DateTime

def now = new DateTime()

def appuntamenti_data = [
    [oggetto: 'appuntamento1', inizio: now],
    [oggetto: 'appuntamento2', inizio: now.plusDays(3)],
    [oggetto: 'appuntamento3', inizio: now.minusDays(5).plusHours(3)],
    [oggetto: 'appuntamento4', inizio: now.plusMonths(1)],
    [oggetto: 'appuntamento5', inizio: now.minusMonths(1)],
]

fixture {
    appuntamenti_data.eachWithIndex {app_data, index ->
        appuntamento"$index"(Appuntamento, oggetto:app_data.oggetto) {
           inizio = app_data.inizio
        }
    }
}
