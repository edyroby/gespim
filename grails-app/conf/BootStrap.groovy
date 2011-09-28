import it.solvingteam.gespim.assegnazione.AreaCompetenza;
import it.solvingteam.gespim.security.*
import it.solvingteam.gespim.tipologiche.StatoPratica
import it.solvingteam.gespim.tipologiche.TipoBeneficiario
import it.solvingteam.gespim.tipologiche.TipoPratica;
import it.solvingteam.gespim.tipologiche.TipoRichiedente
import it.solvingteam.gespim.tipologiche.TipologiaLegale

class BootStrap {
	
	def fixtureLoader
	def grailsApplication

    def init = { servletContext ->
		String webdir = servletContext.getRealPath("/")
		
		prepareUserRole()
		prepareTipologiche(webdir)
		
		//dati di prova
		fixtureLoader.load('pratiche')
		fixtureLoader.load('richiedenti')
    }
    def destroy = {
    }
	
	private void prepareUserRole(){
		def adminRole = new Ruolo(authority: 'ROLE_ADMIN').save(flush: true)
		def userRole = new Ruolo(authority: 'ROLE_USER').save(flush: true)
  
		def testUser = new Utente(username: 'me', enabled: true, password: 'password')
		testUser.save(flush: true)
  
		UtenteRuolo.create testUser, adminRole, true
		
		RequestMap.findByUrlAndConfigAttribute('/**', 'IS_AUTHENTICATED_FULLY')?:new RequestMap(url:'/**', configAttribute:'IS_AUTHENTICATED_FULLY').save()
		RequestMap.findByUrlAndConfigAttribute('/login/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/login/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/logout/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/logout/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/img/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/img/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/css/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/css/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/js/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/js/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		RequestMap.findByUrlAndConfigAttribute('/plugins/**', 'IS_AUTHENTICATED_ANONYMOUSLY')?:new RequestMap(url:'/plugins/**', configAttribute:'IS_AUTHENTICATED_ANONYMOUSLY').save()
		
	}
	
	private void prepareTipologiche(webdir){
		
		//Stato Pratica
		new StatoPratica(codice:StatoPratica.COD_STATO_NON_PRESENTE,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_NON_PRESENTE]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_LAVORATA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_LAVORATA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_IN_ATTESA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_IN_ATTESA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_IN_EVIDENZA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_IN_EVIDENZA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_ATTESA_PARERE_QUESTURA,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_ATTESA_PARERE_QUESTURA]).save()
		new StatoPratica(codice:StatoPratica.COD_STATO_ATTESA_PARERE_DPL,descrizione:StatoPratica.mappaCodici[StatoPratica.COD_STATO_ATTESA_PARERE_DPL]).save()
		
		//Tipologia legale
		new TipologiaLegale(codice:TipologiaLegale.COD_ISTANZA_DI_RIESAME,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ISTANZA_DI_RIESAME]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RIGETTO_MANCATA_INTEGRAZIONE,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RIGETTO_MANCATA_INTEGRAZIONE]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_PARERE_NEGATIVO_QUESTURA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_PARERE_NEGATIVO_QUESTURA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_INAMMISSIBILITA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_INAMMISSIBILITA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RINUNCIA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RINUNCIA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_DISCONOSCIMENTO,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_DISCONOSCIMENTO]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_ASSENZA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ASSENZA]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_ACCESSO_ATTI_DIFFIDE,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_ACCESSO_ATTI_DIFFIDE]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_RICORSI,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_RICORSI]).save()
		new TipologiaLegale(codice:TipologiaLegale.COD_DOCUMENTAZIONE_FALSA,descrizione:TipologiaLegale.mappaCodici[TipologiaLegale.COD_DOCUMENTAZIONE_FALSA]).save()
		
		//AREA COMPETENZA
		def f= new File("${webdir}WEB-INF/initData/area_competenza.csv")
		f.eachLine {
			def row = it.split(",")
			new AreaCompetenza(codice:row[0],descrizione:row[1]).save(flush:true)
		}
		//TIPO PRATICHE
		grailsApplication.config.tipoPratiche?.each{
			new TipoPratica(codice:it.key,descrizione:it.value.nome).save()
		}
		//TIPO RICHIEDENTI
		f = new File("${webdir}WEB-INF/initData/tipo_richiedente.csv")
		f.eachLine {
			def row = it.split(",")
			new TipoRichiedente(codice:row[0],descrizione:row[1]).save(flush:true)
		}
		/*
		grailsApplication.config.tipoRichiedenti?.each{
			new TipoRichiedente(codice:it.key,descrizione:it.value.nome).save()
		}
		*/
		//TIPO BENEFICIARIO
		f = new File("${webdir}WEB-INF/initData/tipo_beneficiario.csv")
		f.eachLine {
			def row = it.split(",")
			new TipoBeneficiario(codice:row[0],descrizione:row[1]).save(flush:true)
		}
		/*
		grailsApplication.config.tipoBeneficiari?.each{
			new TipoBeneficiario(codice:it.key,descrizione:it.value.nome).save()
		}
		*/
		
		
	}
	
}
