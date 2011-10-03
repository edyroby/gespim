package it.solvingteam.gespim.security

import it.solvingteam.gespim.assegnazione.AreaCompetenza;

class Utente {

	transient springSecurityService

	String nome
	String cognome
	
	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	AreaCompetenza area

	static constraints = {
		username blank: false, unique: true
		password blank: false
		nome blank: false
		cognome blank: false
		area nullable:true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Ruolo> getAuthorities() {
		UtenteRuolo.findAllByUtente(this).collect { it.ruolo } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	String toString(){
		return cognome + " " + nome
	}
	
}
