package it.solvingteam.gespim.assegnazione

import it.solvingteam.gespim.pratica.Pratica;

class AssegnazionePratica {
	
	Pratica praticaAssegnata
	AreaCompetenza areaCompetenza
	Date dataAssegnazione
	boolean presaInCarico
	

    static constraints = {
		praticaAssegnata(nullable:false)
		areaCompetenza(nullable:false)
		dataAssegnazione(nullable:true)
    }
	
	
	static AssegnazionePratica findAssegnazioneByPraticaAndUtenza(pratica,user){
		def c = AssegnazionePratica.createCriteria()
		def result = c.list(){
			eq 'praticaAssegnata',pratica
			areaCompetenza{
				utenti{
					eq 'id',user.id
				}
			}
		}
	}
}
