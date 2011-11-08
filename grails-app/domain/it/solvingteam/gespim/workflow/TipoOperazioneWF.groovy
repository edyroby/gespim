package it.solvingteam.gespim.workflow

enum TipoOperazioneWF {
	STAMPA(1), CONVOCAZIONE(2)
	
	TipoOperazioneWF(int value) { 
		this.value = value 
	}
	private final int value
	
	public int value() { return value }
}
