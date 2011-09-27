package it.solvingteam.gespim.pratica

import it.solvingteam.gespim.tipologiche.TipoBeneficiario;

import java.util.Date;

class Beneficiario {
	
	String nome
	String cognome
	Date dataNascita
	String cittadinanza
	
	TipoBeneficiario tipoBeneficiario

    static constraints = {
		nome(nullable:true)
		cognome(nullable:true)
		dataNascita(nullable:true)
		cittadinanza(nullable:true)
		tipoBeneficiario(nullable:true)
    }
}
