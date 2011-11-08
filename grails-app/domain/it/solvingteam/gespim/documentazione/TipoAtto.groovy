package it.solvingteam.gespim.documentazione

enum TipoAtto {
	PREVIA_VIDMAZIONE(1), IMMEDIATA(2),SCHEDULATA(3)
	
	TipoAtto(int value) {
		this.value = value
	}
	private final int value
	
	public int value() { return value }
}
