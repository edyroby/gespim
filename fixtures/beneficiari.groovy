import it.solvingteam.gespim.pratica.Beneficiario;
import it.solvingteam.gespim.tipologiche.TipoBeneficiario;

fixture {
	
	beneficiario1(Beneficiario,nome:'Mario',cognome:'Bianchi',dataNascita: new Date(),cittadinanza:'italiana'){
		tipoBeneficiario = TipoBeneficiario.findByCodice(TipoBeneficiario.COD_LAVORATORE)
	}
	
	beneficiario2(Beneficiario,nome:'Yassel',cognome:'Buud',dataNascita: new Date(),cittadinanza:'libica'){
		tipoBeneficiario = TipoBeneficiario.findByCodice(TipoBeneficiario.COD_STRANIERO)
	}
	
	beneficiario3(Beneficiario,nome:'Abdal',cognome:'Bismark',dataNascita: new Date(),cittadinanza:'tunisino'){
		tipoBeneficiario = TipoBeneficiario.findByCodice(TipoBeneficiario.COD_RICONGIUNTO)
	}
	beneficiario4(Beneficiario,nome:'Aal',cognome:'Bism',dataNascita: new Date(),cittadinanza:'tunisino'){
		tipoBeneficiario = TipoBeneficiario.findByCodice(TipoBeneficiario.COD_RICONGIUNTO)
	}
	
	lavoratore2(Beneficiario,nome:'Kaal',cognome:'Maal',dataNascita: new Date(),cittadinanza:'nigeriana'){
		tipoBeneficiario = TipoBeneficiario.findByCodice(TipoBeneficiario.COD_LAVORATORE)
	}
	
}