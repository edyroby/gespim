package it.solvingteam.gespim.security

import org.apache.commons.lang.builder.HashCodeBuilder

class UtenteRuolo implements Serializable {

	Utente utente
	Ruolo ruolo

	boolean equals(other) {
		if (!(other instanceof UtenteRuolo)) {
			return false
		}

		other.utente?.id == utente?.id &&
			other.ruolo?.id == ruolo?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (utente) builder.append(utente.id)
		if (ruolo) builder.append(ruolo.id)
		builder.toHashCode()
	}

	static UtenteRuolo get(long utenteId, long ruoloId) {
		find 'from UtenteRuolo where utente.id=:utenteId and ruolo.id=:ruoloId',
			[utenteId: utenteId, ruoloId: ruoloId]
	}

	static UtenteRuolo create(Utente utente, Ruolo ruolo, boolean flush = false) {
		new UtenteRuolo(utente: utente, ruolo: ruolo).save(flush: flush, insert: true)
	}

	static boolean remove(Utente utente, Ruolo ruolo, boolean flush = false) {
		UtenteRuolo instance = UtenteRuolo.findByUtenteAndRuolo(utente, ruolo)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Utente utente) {
		executeUpdate 'DELETE FROM UtenteRuolo WHERE utente=:utente', [utente: utente]
	}

	static void removeAll(Ruolo ruolo) {
		executeUpdate 'DELETE FROM UtenteRuolo WHERE ruolo=:ruolo', [ruolo: ruolo]
	}

	static mapping = {
		id composite: ['ruolo', 'utente']
		version false
	}
}
