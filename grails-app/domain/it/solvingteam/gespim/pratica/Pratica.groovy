package it.solvingteam.gespim.pratica

import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.StatoPratica;
import it.solvingteam.gespim.tipologiche.TipologiaLegale;

class Pratica {

	//codice registrazione del tracciato excel
	String numeroPratica
	String codiceIstanza
	String codiceQuestura
	
	TipoPratica tipoPratica
	
	TipologiaLegale tipologiaLegale
	StatoPratica statoPratica
	
	Richiedente richiedente
	
	static hasMany = [beneficiari:Beneficiario]


	static constraints = {
		numeroPratica(blank:false,unique:true)
		codiceIstanza(blank:false,unique:true)
		codiceQuestura(blank:false,unique:true,maxSize:8)
		statoPratica(nullable:true)
		tipologiaLegale(nullable:true)
		tipoPratica(nullable:false)
		richiedente(nullable:true)
		beneficiari(nullable:true)
	}
	
	def static cercaPratiche(cmd,params){
		def c = Pratica.createCriteria()

		def results = c.list(params){

			if(cmd.numeroPratica){
				ilike 'numeroPratica', "%${cmd.numeroPratica}%"
			}
			if(cmd.codiceQuestura){
				ilike 'codiceQuestura', "%${cmd.codiceQuestura}%"
			}
			if(cmd.codiceIstanza){
				ilike 'codiceIstanza', "%${cmd.codiceIstanza}%"
			}
			if(cmd.statoPratica){
				eq 'statoPratica.id', cmd.statoPratica as long
			}
			if(cmd.tipoPratica){
				eq 'tipoPratica.id', cmd.tipoPratica as long
			}
			if(cmd.tipologiaLegale){
				eq 'tipologiaLegale.id', cmd.tipologiaLegale as long
			}
			if(cmd.nomeRichiedente){
				richiedente{
					ilike 'nome', "%${cmd.nomeRichiedente}%"
				}
			}
			if(cmd.cognomeRichiedente){
				richiedente{
					ilike 'cognome', "%${cmd.cognomeRichiedente}%"
				}
			}
			if(cmd.dataNascitaRichiedente){
				richiedente{
					eq 'dataNascita', cmd.dataNascitaRichiedente
				}
			}
			if(cmd.beneficiari){
				println "...........beneficiari:     "+cmd.beneficiari
				beneficiari{
					'in'("id",cmd.beneficiari?.collect{it.id})
				}
			}

		}

	}

	
}
