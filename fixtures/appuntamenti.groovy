import it.solvingteam.gespim.calendario.Appuntamento
import org.joda.time.DateTime

def appuntamenti_data = [
    [oggetto: 'appuntamento1', inizio: new DateTime(2011, 4, 10, 11, 20, 0, 0)],
    [oggetto: 'appuntamento2', inizio: new DateTime(2011, 5, 7, 8, 40, 0, 0)],
    [oggetto: 'appuntamento3', inizio: new DateTime(2011, 6, 10, 12, 0, 0, 0)],
    [oggetto: 'appuntamento4', inizio: new DateTime(2011, 6, 13, 9, 30, 0, 0)],
    [oggetto: 'appuntamento5', inizio: new DateTime(2011, 7, 10, 15, 10, 0, 0)],
]

fixture {
    appuntamenti_data.eachWithIndex {app_data, index ->
        appuntamento"$index"(Appuntamento, oggetto:app_data.oggetto) {
           inizio = app_data.inizio
        }
    }
}
