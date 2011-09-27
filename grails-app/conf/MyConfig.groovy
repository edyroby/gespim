import it.solvingteam.gespim.pratica.* 

tipoPratiche{
	TP01{
		nome = 'Pratica Articolo 27'
		controller = 'PraticaArticolo27Controller'
		type = PraticaArticolo27.class
	}
	TP02{
		nome = 'Conversione Studio Lavoro'
		controller = 'PraticaConversioneStudLavController'
		type = PraticaConversioneStudLav.class
	}
	TP03{
		nome = 'Emersione'
		controller = 'PraticaEmersioneController'
		type = PraticaEmersione.class
	}
	TP04{
		nome = 'Flussi'
		controller = 'PraticaFlussiController'
		type = PraticaFlussi.class
	}
	TP05{
		nome = 'Flussi Stagionali'
		controller = 'PraticaFlussiStagionaliController'
		type = PraticaFlussiStagionali.class
	}
	TP06{
		nome = 'Neo Comunitari'
		controller = 'PraticaNeoComunitariController'
		type = PraticaNeoComunitari.class
	}
	TP07{
		nome = 'Ric Fam'
		controller = 'PraticaRicongiungimentiFamiliariController'
		type = PraticaRicongiungimentiFamiliari.class
	}
	/*
	TP08{
		nome = 'Test di Italiano'
		controller = 'PraticaTestItalianoController'
		type = PraticaTestItaliano.class
	}
	*/
}

tipoRichiedenti{
	TR01{
		nome = 'Datore'
	}
	TR02{
		nome = 'Azienda'
	}
	TR03{
		nome = 'Richiedente Ricongiungimento'
	}
}

tipoBeneficiari{
	TB01{
		nome = 'Lavoratore'
	}
	TB02{
		nome = 'Straniero'
	}
	TB03{
		nome = 'Ricongiunto'
	}
}

