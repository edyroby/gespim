package it.solvingteam.gespim.security

class Ruolo implements org.activiti.engine.identity.Group{
	
	static final String ROLE_ADMIN = "ROLE_ADMIN"
	static final String ROLE_RESPONSABILE_AREA_LEGALE = "ROLE_RESPONSABILE_AREA_LEGALE"
	static final String ROLE_USER_AREA_LEGALE = "ROLE_USER_AREA_LEGALE"
	static final String ROLE_USER = "ROLE_USER"
	static final String ROLE_PROTOCOLLO = "ROLE_PROTOCOLLO"

	String id
	String name
	String authority
	String type

	static mapping = {
		cache true
		id generator: 'assigned'
	}

	static constraints = {
		authority blank: false, unique: true
		name blank: false
		type nullable: true
	}
}
