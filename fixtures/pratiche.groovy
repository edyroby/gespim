import it.solvingteam.gespim.pratica.Pratica
import it.solvingteam.gespim.tipologiche.StatoPratica;
import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.TipologiaLegale;

include "beneficiari"
include "richiedenti"

fixture {
	
	pratica_art27_1(Pratica,numeroPratica:'P-RM/L/N/2008/123422',codiceIstanza:'4567',codiceQuestura:'asdf'){
		tipoPratica = TipoPratica.findByCodice('TP01')
		beneficiari = [beneficiario1]
		richiedente = azienda1
	}
	pratica_flussi1(Pratica,numeroPratica:'P-RM/L/Q/2008/123422',codiceIstanza:'4577',codiceQuestura:'aslf'){
		tipoPratica = TipoPratica.findByCodice('TP04')
		beneficiari = [beneficiario1]
		richiedente = datore1
		tipologiaLegale = TipologiaLegale.findByCodice(TipologiaLegale.COD_ISTANZA_DI_RIESAME)
	}
	pratica_flussi2(Pratica,numeroPratica:'P-RM/L/Q/2008/123423',codiceIstanza:'4578',codiceQuestura:'aslf1'){
		tipoPratica = TipoPratica.findByCodice('TP04')
		beneficiari = [beneficiario1]
		statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_IN_EVIDENZA)
		richiedente = datore2
	}
	pratica_flussistag1(Pratica,numeroPratica:'P-RM/L/Q/2008/100445',codiceIstanza:'4767',codiceQuestura:'abdf'){
		tipoPratica = TipoPratica.findByCodice('TP05')
		beneficiari = [lavoratore2]
		richiedente = datore2
	}
	pratica_emersione1(Pratica,numeroPratica:'P-RM/L/N/2009/123446',codiceIstanza:'4867',codiceQuestura:'agdf'){
		tipoPratica = TipoPratica.findByCodice('TP03')
		beneficiari = [lavoratore2]
		statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_LAVORATA)
		richiedente = datore1
	}
	pratica_emersione2(Pratica,numeroPratica:'P-RM/L/N/2009/123447',codiceIstanza:'4868',codiceQuestura:'agdf0'){
		tipoPratica = TipoPratica.findByCodice('TP03')
		beneficiari = [lavoratore2]
		statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_IN_ATTESA)
		richiedente = datore2
	}
	pratica_RicFam1(Pratica,numeroPratica:'P-RM/F/N/2008/104446',codiceIstanza:'4857',codiceQuestura:'agtf'){
		tipoPratica = TipoPratica.findByCodice('TP07')
		beneficiari = [beneficiario3,beneficiario4]
		richiedente = richRicong1
		statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_LAVORATA)
	}
	pratica_RicFam2(Pratica,numeroPratica:'P-RM/F/N/2008/104447',codiceIstanza:'4858',codiceQuestura:'agtf1'){
		tipoPratica = TipoPratica.findByCodice('TP07')
		beneficiari = [beneficiario2]
		statoPratica = StatoPratica.findByCodice(StatoPratica.COD_STATO_ATTESA_PARERE_DPL)
	}
	
	
}