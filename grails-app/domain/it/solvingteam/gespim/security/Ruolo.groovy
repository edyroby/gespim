package it.solvingteam.gespim.security

class Ruolo {

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
