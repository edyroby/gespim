package it.solvingteam.gespim.security

class Ruolo {
	
	static final String ROLE_ADMIN = "ROLE_ADMIN"
	static final String ROLE_USER = "ROLE_USER"
	static final String ROLE_PROTOCOLLO = "ROLE_PROTOCOLLO"

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
