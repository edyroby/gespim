package it.solvingteam.gespim.workflow

class Transizione {
	
	static final int TIPO_TRANSIZIONE_MANUALE = 0
	static final int TIPO_TRANSIZIONE_SCHEDULATA = 1
	static final int TIPO_TRANSIZIONE_PREVIA_APPROVAZIONE = 2
	
	Stato statoAttuale
	Stato statoSuccessivo
	Integer tipoTransizione
	Integer intervalloGG
	
	

    static constraints = {
		statoAttuale(nullable:true)
		statoSuccessivo(nullable:true)
		tipoTransizione(nullable:true)
		intervalloGG(nullable:true)
    }
}
