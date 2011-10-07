package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.pratica.Pratica;
import it.solvingteam.gespim.security.Utente

class AssegnazionePratica {
	
	Pratica praticaAssegnata
	AreaCompetenza areaCompetenza
	Utente utenteAssegnante
	Date dataAssegnazione
	Date dataPresaInCarico
	boolean presaInCarico
	Utente utentePresaInCarico
	
	

    static constraints = {
		praticaAssegnata(nullable:false)
		areaCompetenza(nullable:false)
		dataAssegnazione(nullable:true)
		dataPresaInCarico(nullable:true)
		utenteAssegnante(nullable:true)
		utentePresaInCarico(nullable:true)
    }
	
	
	static AssegnazionePratica findAssegnazioneByPraticaAndUtenza(pratica,user){
		def c = AssegnazionePratica.createCriteria()
		def result = c.get(){
			eq 'praticaAssegnata',pratica
			areaCompetenza{
				utenti{
					eq 'id',user.id
				}
			}
		}
	}
}
